package com.intent.tianjian.controller;

import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mysql.SaveModeToDataBaseService;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.service.ProductService;
import com.intent.tianjian.spring.LogPrint;
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
    private SaveModeToDataBaseService saveModeToDataBaseService;

    @GetMapping("/batchCreate")
    @LogPrint
    public boolean createSomeProduct(@RequestParam("count") Integer count) {
        boolean result = productService.clearData();
        return productService.createProductByCountParam(count);
    }

    @GetMapping("/changeCost")
    public Product changCostComponent(@RequestParam("componentId") Long componentId,
                                   @RequestParam("fixedCost") Integer fixedCost) {
        return productService.changeComponentCost(componentId, fixedCost);

    }

    //9ls 34553 34c53032-c17d-466a-9234-33da692d52e9

    @GetMapping("/getProductById")
    public Product getProductById(@RequestParam("componentId") String componentId,
                                      @RequestParam("id") Long id) {
        System.out.println(JSONObject.toJSONString(productService.getProductByNeo4j(id)));
        System.out.println(JSONObject.toJSONString(saveModeToDataBaseService.getProductByComponentId(componentId)));
        return null;

    }

    @GetMapping("/search")
    public Product getProductByComponentId(@RequestParam("componentId") Long componentId) {
        Product product = productService.getProductByNeo4j(componentId);
        if(product != null) {
            return product;
        }
        return null;
    }



}
