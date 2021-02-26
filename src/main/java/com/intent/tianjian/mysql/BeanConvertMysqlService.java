package com.intent.tianjian.mysql;

import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.mysql.curd.ComponentCurd;
import com.intent.tianjian.mysql.curd.ContainsRelationCurd;
import com.intent.tianjian.mysql.curd.ProductCurd;
import com.intent.tianjian.mysql.eo.ComponentEo;
import com.intent.tianjian.mysql.eo.ContainsRelationEo;
import com.intent.tianjian.mysql.eo.ProductEo;
import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.ComponentRelation;
import com.intent.tianjian.product.ContainRelation;
import com.intent.tianjian.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class BeanConvertMysqlService {

    @Autowired
    private ComponentCurd componentCurd;

    @Autowired
    private ContainsRelationCurd containsRelationCurd;

    @Autowired
    private ProductCurd productCurd;

//    private Map<String,Object> datas = new HashMap<>();
//
//    private Map<String, List<ContainsRelationEo>> realtions = new HashMap<>();

    public boolean saveProductToMysql(Product product, String id) {

        ProductEo productEo = new ProductEo();
        productEo.setId(id);
        productEo.setTotalCost(product.getTotalCost());
        productEo.setProductName(product.getName());

        productCurd.save(productEo);

        for(ComponentRelation relation :product.getComponentRelations()) {

            ComponentEo componentEo = new ComponentEo();
            Component component = relation.getComponent();
            componentEo.setComponentName(component.getName());
            componentEo.setFixedCost(component.getFixedCost());
            componentEo.setTotalCost(component.getTotalCost());
            componentEo.setId(UUID.randomUUID().toString());
            componentEo.setProductId(productEo.getId());
            componentCurd.save(componentEo);

            ContainsRelationEo containsRelationEo = new ContainsRelationEo();
            containsRelationEo.setSourceId(productEo.getId());
            containsRelationEo.setWeight(relation.getWeight());
            containsRelationEo.setTargetId(componentEo.getId());
            containsRelationEo.setType("1");
            containsRelationEo.setId(UUID.randomUUID().toString());
            containsRelationEo.setProductId(productEo.getId());
            containsRelationCurd.save(containsRelationEo);
            addContainsRelation(componentEo.getId(), component.getContainRelations(), productEo.getId());
        }

        return true;
    }


    private void addContainsRelation(String parentId, Set<ContainRelation> containRelations, String productId) {
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
            componentCurd.save(componentEo);
            ContainsRelationEo containsRelationEo = new ContainsRelationEo();
            containsRelationEo.setSourceId(parentId);
            containsRelationEo.setWeight(containRelation.getWeight());
            containsRelationEo.setTargetId(componentEo.getId());
            containsRelationEo.setType("2");
            containsRelationEo.setId(UUID.randomUUID().toString());
            containsRelationEo.setProductId(productId);
            containsRelationCurd.save(containsRelationEo);
            addContainsRelation(componentEo.getId(), component.getContainRelations(), productId);
        }
    }

    public Product getProductByProductId(String productId) {
        Product product = new Product();
        ProductEo productEo = productCurd.findById(productId).get();
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
}
