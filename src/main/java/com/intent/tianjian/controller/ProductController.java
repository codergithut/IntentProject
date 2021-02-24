package com.intent.tianjian.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONString;
import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.product.ProductRepository;
import com.intent.tianjian.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Created by tianjian on 2021/2/23.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;



    @GetMapping("/batchCreate")
    public boolean createSomeProduct(@RequestParam("count") Integer count) {
        return productService.createProductByCountParam(count);
    }

    @GetMapping("/changeCost")
    public Product changCostComponent(@RequestParam("componentId") Long componentId,
                                   @RequestParam("fixedCost") Integer fixedCost) {
        return productService.changeComponentCost(componentId, fixedCost);

    }

    @GetMapping("/test")
    public String getValue() {
        Product product = productService.getProductByComponentId(22L);
        if(product != null) {
            return JSONObject.toJSONString(product);
        }
        return null;
    }



}
