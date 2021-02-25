package com.intent.tianjian.mysql;

import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.mysql.eo.ComponentEo;
import com.intent.tianjian.mysql.eo.ContainsRelationEo;
import com.intent.tianjian.mysql.eo.ProduectEo;
import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.ComponentRelation;
import com.intent.tianjian.product.ContainRelation;
import com.intent.tianjian.product.Product;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class BeanConvertUtil {

    private static Map<String,Object> datas = new HashMap<>();

    private static Map<String, List<ContainsRelationEo>> realtions = new HashMap<>();

    public static boolean saveProductToMysql(Product product, String id) {
        ProduectEo produectEo = new ProduectEo();
        produectEo.setId(id);
        produectEo.setTotalCost(product.getTotalCost());
        produectEo.setProductName(product.getName());

        datas.put(produectEo.getId(), produectEo);

        for(ComponentRelation relation :product.getComponentRelations()) {

            ComponentEo componentEo = new ComponentEo();
            Component component = relation.getComponent();
            componentEo.setComponentName(component.getName());
            componentEo.setFixedCost(component.getFixedCost());
            componentEo.setTotalCost(component.getTotalCost());
            componentEo.setId(UUID.randomUUID().toString());
            datas.put(componentEo.getId(), componentEo);

            ContainsRelationEo containsRelationEo = new ContainsRelationEo();
            containsRelationEo.setSourceId(produectEo.getId());
            containsRelationEo.setWeight(relation.getWeight());
            containsRelationEo.setTargetId(componentEo.getId());
            containsRelationEo.setType("1");
            containsRelationEo.setId(UUID.randomUUID().toString());
            addValue(produectEo.getId(),containsRelationEo);
            addContainsRelation(componentEo.getId(), component.getContainRelations());
        }

        return true;
    }

    public static void addValue(String key, ContainsRelationEo t) {
        if(!realtions.containsKey(key)) {
            List<ContainsRelationEo> s = new ArrayList<>();
            realtions.put(key, s);
        }
        realtions.get(key).add(t);
    }



    private static void addContainsRelation(String parentId, Set<ContainRelation> containRelations) {
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
            ContainsRelationEo containsRelationEo = new ContainsRelationEo();
            containsRelationEo.setSourceId(parentId);
            containsRelationEo.setWeight(containRelation.getWeight());
            containsRelationEo.setTargetId(componentEo.getId());
            containsRelationEo.setType("2");
            containsRelationEo.setId(UUID.randomUUID().toString());
            addContainsRelation(componentEo.getId(), component.getContainRelations());
            addValue(containsRelationEo.getSourceId(), containsRelationEo);
            datas.put(componentEo.getId(), componentEo);
        }
    }

    public static Product getProductByData(String productId) {
        Product product = new Product();
        ProduectEo productEo = (ProduectEo) datas.get(productId);
        product.setName(productEo.getProductName());
        product.setTotalCost(productEo.getTotalCost());

        List<ContainsRelationEo> containsRelationEos = realtions.get(productEo.getId());

        if(!CollectionUtils.isEmpty(containsRelationEos)) {
            for(ContainsRelationEo cn : containsRelationEos) {
                ComponentEo componentEo = (ComponentEo) datas.get(cn.getTargetId());
                product.addComponentRelation(addContainsByEo(componentEo));
            }

        }
        return product;

    }

    private static Component addContainsByEo(ComponentEo componentEo) {
        Component component = new Component();
        component.setName(componentEo.getComponentName());
        component.setFixedCost(componentEo.getFixedCost());
        List<ContainsRelationEo> containsRelationEos = realtions.get(componentEo.getId());
        if(CollectionUtils.isEmpty(containsRelationEos)) {
            return component;
        }
        for(ContainsRelationEo containsRelationEo : containsRelationEos) {
            ComponentEo det = (ComponentEo) datas.get(containsRelationEo.getTargetId());
            component.addContainsRelation(addContainsByEo(det));
        }
        return component;
    }

    public static void main(String[] args) {
        Product product = CreateProductFactory.createProduct(2, 2);

        String uuid = UUID.randomUUID().toString();

        saveProductToMysql(product, uuid);

        Product ss = getProductByData(uuid);

        System.out.println(JSONObject.toJSON(product));
        System.out.println(JSONObject.toJSON(ss));

        System.out.println("sss");
    }
}
