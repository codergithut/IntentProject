package com.intent.tianjian.mock;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.ComponentRelation;
import com.intent.tianjian.product.ContainRelation;
import com.intent.tianjian.product.Product;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tianjian on 2021/2/23.
 */
public class CreateProductFactory {

    public static Product createProduct(int dep, int nodeMax) {
        Product product = new Product();

        int i = RandomUtil.randomInt(nodeMax) + 2;

        product.setName(RandomUtil.randomString(9));

        while(i > 0) {
            i--;
            Component component = new Component();
            component.setName(RandomUtil.randomString(6));
            component.setFixedCost(RandomUtil.randomInt(10) + 1);
            component.setName(RandomUtil.randomString(3));
            component.setDepNum(0);
            component.addContainsRelation(createComponent(dep, nodeMax, 0));
            product.addComponentRelation(component);
        }
        return product;
    }

    public static Component createComponent(int dep, int nodeMax, int initDep) {
        Component component = new Component();
        component.setName(RandomUtil.randomString(3));
        component.setFixedCost(RandomUtil.randomInt(4) + 1);
        component.setDepNum(initDep);
        if(initDep < dep) {
            initDep++;
            if(initDep < dep) {
                int maxNode = RandomUtil.randomInt(nodeMax);
                while(maxNode > 0) {
                    maxNode--;
                    Component detail = createComponent(dep, nodeMax, initDep);
                    component.addContainsRelation(detail);
                }
            }

        }
        return component;
    }

}
