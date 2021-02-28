package com.intent.tianjian.mysql;

import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.mysql.curd.ComponentCurd;
import com.intent.tianjian.mysql.curd.ContainsRelationCurd;
import com.intent.tianjian.mysql.curd.ProductCurd;
import com.intent.tianjian.mysql.eo.ComponentEo;
import com.intent.tianjian.mysql.eo.ContainsRelationEo;
import com.intent.tianjian.mysql.eo.ProductEo;
import com.intent.tianjian.product.*;
import com.intent.tianjian.spring.LogPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class SaveModeToDataBaseService {

    @Autowired
    private ComponentCurd componentCurd;

    @Autowired
    private ContainsRelationCurd containsRelationCurd;

    @Autowired
    private ProductCurd productCurd;

    @Autowired
    private ProductRepository productRepository;

//    private Map<String,Object> datas = new HashMap<>();
//
//    private Map<String, List<ContainsRelationEo>> realtions = new HashMap<>();

    @LogPrint
    public boolean addProductToNeo4j(Product product) {
        productRepository.save(product);
        return true;
    }

    @LogPrint
    public boolean saveProductToMysql(Product product, String id) {

        MysqlEoStore mysqlEoStore = new MysqlEoStore();

        ProductEo productEo = new ProductEo();
        productEo.setId(id);
        productEo.setTotalCost(product.getTotalCost());
        productEo.setProductName(product.getName());
        mysqlEoStore.addProductEo(productEo);

        //productCurd.save(productEo);

        for(ComponentRelation relation :product.getComponentRelations()) {

            ComponentEo componentEo = new ComponentEo();
            Component component = relation.getComponent();
            componentEo.setComponentName(component.getName());
            componentEo.setFixedCost(component.getFixedCost());
            componentEo.setTotalCost(component.getTotalCost());
            componentEo.setId(UUID.randomUUID().toString());
            componentEo.setProductId(productEo.getId());
            mysqlEoStore.addComponentEo(componentEo);
            //componentCurd.save(componentEo);

            ContainsRelationEo containsRelationEo = new ContainsRelationEo();
            containsRelationEo.setSourceId(productEo.getId());
            containsRelationEo.setWeight(relation.getWeight());
            containsRelationEo.setTargetId(componentEo.getId());
            containsRelationEo.setType("1");
            containsRelationEo.setId(UUID.randomUUID().toString());
            containsRelationEo.setProductId(productEo.getId());
            mysqlEoStore.addContainsRelationEo(containsRelationEo);
            //containsRelationCurd.save(containsRelationEo);
            addContainsRelation(componentEo.getId(), component.getContainRelations(), productEo.getId(), mysqlEoStore);
        }
        mysqlEoStore.saveToMySql();
        return true;
    }


    private void addContainsRelation(String parentId, Set<ContainRelation> containRelations,
                                     String productId, MysqlEoStore mysqlEoStore) {
        if(CollectionUtils.isEmpty(containRelations)) {
            return ;
        }

        for(ContainRelation containRelation : containRelations) {
            ComponentEo componentEo = new ComponentEo();
            Component component = containRelation.getComponent();
            componentEo.setComponentName(component.getName());
            componentEo.setFixedCost(component.getFixedCost());
            componentEo.setTotalCost(component.getTotalCost());
            componentEo.setId(UUID.randomUUID().toString());
            componentEo.setProductId(productId);
            mysqlEoStore.addComponentEo(componentEo);
            //componentCurd.save(componentEo);
            ContainsRelationEo containsRelationEo = new ContainsRelationEo();
            containsRelationEo.setSourceId(parentId);
            containsRelationEo.setWeight(containRelation.getWeight());
            containsRelationEo.setTargetId(componentEo.getId());
            containsRelationEo.setType("2");
            containsRelationEo.setId(UUID.randomUUID().toString());
            containsRelationEo.setProductId(productId);
            mysqlEoStore.addContainsRelationEo(containsRelationEo);
            //containsRelationCurd.save(containsRelationEo);
            addContainsRelation(componentEo.getId(), component.getContainRelations(), productId, mysqlEoStore);
        }
    }

    @LogPrint
    public Product getProductByComponentId(String componentId) {
        Product product = new Product();
        ComponentEo v = componentCurd.findById(componentId).get();
        ProductEo productEo = productCurd.findById(v.getProductId()).get();
        List<ContainsRelationEo> containsRelationEos = containsRelationCurd
                .findByProductId(productEo.getId());
        List<ComponentEo> componentEos = componentCurd.findByProductId(productEo.getId());
        GrpicMeat grpicMeat = initMetaData(productEo, componentEos, containsRelationEos);
        product.setName(productEo.getProductName());
        product.setTotalCost(productEo.getTotalCost());

        List<ContainsRelationEo> productRelations = grpicMeat.getSourceRelation(productEo.getId());
        if(!CollectionUtils.isEmpty(productRelations)) {
            for(ContainsRelationEo cn : productRelations) {
                ComponentEo componentEo = (ComponentEo) grpicMeat.getDataByKey(cn.getTargetId());
                product.addComponentRelation(addContainsByEo(componentEo, grpicMeat));
            }
        }
        return product;
    }

    private Component addContainsByEo(ComponentEo componentEo, GrpicMeat grpicMeat) {
        Component component = new Component();
        component.setName(componentEo.getComponentName());
        component.setFixedCost(componentEo.getFixedCost());
        List<ContainsRelationEo> containsRelationEos = grpicMeat.getSourceRelation(componentEo.getId());
        if(CollectionUtils.isEmpty(containsRelationEos)) {
            return component;
        }
        for(ContainsRelationEo containsRelationEo : containsRelationEos) {
            ComponentEo det = (ComponentEo) grpicMeat.getDataByKey(containsRelationEo.getTargetId());
            component.addContainsRelation(addContainsByEo(det, grpicMeat));
        }
        return component;
    }

    public GrpicMeat initMetaData(ProductEo productEo, List<ComponentEo> componentEos, List<ContainsRelationEo> containsRelationEos) {
        GrpicMeat grpicMeat = new GrpicMeat();

        grpicMeat.addDataByKey(productEo.getId(), productEo);
        componentEos.forEach(e -> {
            grpicMeat.addDataByKey(e.getId(), e);
        });
        containsRelationEos.forEach(e -> {
            grpicMeat.addValue(e.getSourceId(), e);
        });
        return grpicMeat;
    }

    public void initDataBase() {
        containsRelationCurd.deleteAll();
        productCurd.deleteAll();
        componentCurd.deleteAll();
    }

    class GrpicMeat {

        private Map<String,Object> datas = new HashMap<>();

        private Map<String, List<ContainsRelationEo>> realtions = new HashMap<>();

        public void addDataByKey(String key, Object v) {
            datas.put(key, v);
        }

        public Object getDataByKey(String key) {
            return datas.get(key);
        }

        public List<ContainsRelationEo> getSourceRelation(String sourceId) {
            return realtions.get(sourceId);
        }

        public void addValue(String key, ContainsRelationEo t) {
            if(!realtions.containsKey(key)) {
                List<ContainsRelationEo> s = new ArrayList<>();
                realtions.put(key, s);
            }
            realtions.get(key).add(t);
        }
    }

    class MysqlEoStore {
        private List<ProductEo> productEos = new ArrayList<>();

        private List<ContainsRelationEo> containsRelationEos = new ArrayList<>();

        private List<ComponentEo> componentEos = new ArrayList<>();

        public List<ProductEo> getProductEos() {
            return productEos;
        }

        public void addProductEo(ProductEo productEo) {
            productEos.add(productEo);
        }

        public void addContainsRelationEo(ContainsRelationEo containsRelationEo) {
            containsRelationEos.add(containsRelationEo);
        }

        public void addComponentEo(ComponentEo componentEo) {
            componentEos.add(componentEo);
        }

        public List<ContainsRelationEo> getContainsRelationEos() {
            return containsRelationEos;
        }


        public List<ComponentEo> getComponentEos() {
            return componentEos;
        }

        @LogPrint
        public void saveToMySql() {
            containsRelationCurd.saveAll(containsRelationEos);
            componentCurd.saveAll(componentEos);
            productCurd.saveAll(productEos);
        }

    }
}
