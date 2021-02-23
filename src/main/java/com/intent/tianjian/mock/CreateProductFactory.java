package com.intent.tianjian.mock;

import cn.hutool.core.util.RandomUtil;
import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.Product;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tianjian on 2021/2/23.
 */
public class CreateProductFactory {
    public static Product createProduct() {
        Component huohuasai = new Component();
        huohuasai.setFixedCost(RandomUtil.randomInt());
        huohuasai.setTotalCost(0);
        huohuasai.setName("火花塞");

        Component dahuoshi = new Component();
        dahuoshi.setName("打火石");
        dahuoshi.setTotalCost(0);
        dahuoshi.setFixedCost(RandomUtil.randomInt());
        huohuasai.addComponent(dahuoshi);

        Component taozhi = new Component();
        taozhi.setName("套子");
        taozhi.setTotalCost(0);
        taozhi.setFixedCost(RandomUtil.randomInt());
        huohuasai.addComponent(taozhi);

        Component chepi = new Component();
        chepi.setFixedCost(RandomUtil.randomInt());
        chepi.setTotalCost(0);
        chepi.setName("车大皮");

        Component lungu = new Component();
        lungu.setName("轮毂");
        lungu.setFixedCost(RandomUtil.randomInt());
        lungu.setTotalCost(0);

        Component lunzi = new Component();
        lunzi.setName("轮子");
        lunzi.setFixedCost(RandomUtil.randomInt());
        lunzi.setTotalCost(0);
        lunzi.addComponent(chepi);
        lunzi.addComponent(lungu);

        Set<Component> componentSet = new HashSet<>();
        componentSet.add(huohuasai);
        componentSet.add(lunzi);

        Product product = new Product();
        product.setFixedCost(RandomUtil.randomInt());
        product.setName("机器人" + RandomUtil.randomString(3));
        product.setTotalCost(0);
        product.setComponents(componentSet);

        return product;
    }
}
