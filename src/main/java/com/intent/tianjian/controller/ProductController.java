package com.intent.tianjian.controller;

import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mysql.BeanConvertMysqlService;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.product.ProductRepository;
import com.intent.tianjian.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tianjian on 2021/2/23.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BeanConvertMysqlService beanConvertMysqlService;

    @GetMapping("/batchCreate")
    public boolean createSomeProduct(@RequestParam("count") Integer count) {
        boolean result = productService.clearData();
        return productService.createProductByCountParam(count);
    }

    @GetMapping("/changeCost")
    public Product changCostComponent(@RequestParam("componentId") Long componentId,
                                   @RequestParam("fixedCost") Integer fixedCost) {
        return productService.changeComponentCost(componentId, fixedCost);

    }

    @GetMapping("/search")
    public Product getProductByComponentId(@RequestParam("componentId") Long componentId) {
        Product product = productService.getProductByComponentId(componentId);
        if(product != null) {
            return product;
        }
        return null;
    }



}
