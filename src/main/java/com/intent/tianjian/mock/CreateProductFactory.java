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
        huohuasai.setFixedCost(RandomUtil.randomInt(100));
        huohuasai.setTotalCost(0);
        huohuasai.setName("火花塞");

        Component dahuoshi = new Component();
        dahuoshi.setName("打火石");
        dahuoshi.setTotalCost(0);
        dahuoshi.setFixedCost(RandomUtil.randomInt(200));
        huohuasai.addContainsRelation(dahuoshi);

        Component taozhi = new Component();
        taozhi.setName("套子");
        taozhi.setTotalCost(0);
        taozhi.setFixedCost(RandomUtil.randomInt(180));
        huohuasai.addContainsRelation(taozhi);

        Component chepi = new Component();
        chepi.setFixedCost(RandomUtil.randomInt(220));
        chepi.setTotalCost(0);
        chepi.setName("车大皮");

        Component lungu = new Component();
        lungu.setName("轮毂");
        lungu.setFixedCost(RandomUtil.randomInt(321));
        lungu.setTotalCost(0);

        Component lunzi = new Component();
        lunzi.setName("轮子");
        lunzi.setFixedCost(RandomUtil.randomInt(123));
        lunzi.setTotalCost(0);
        lunzi.addContainsRelation(chepi);
        lunzi.addContainsRelation(lungu);

        Product product = new Product();
        product.setFixedCost(RandomUtil.randomInt(131));
        product.setName("机器人" + RandomUtil.randomString(3));
        product.setTotalCost(0);
        product.addComponentRelation(huohuasai);
        product.addComponentRelation(lunzi);
        product.setTotalCost(product.countTotalCost());
        return product;
    }
}
