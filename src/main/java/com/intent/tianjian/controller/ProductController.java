package com.intent.tianjian.controller;

import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.product.ProductRepository;
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
    private ProductRepository productRepository;

    public void createSomeProduct(@RequestParam("count") Integer count) {
        for(int i = 0; i < count; i++) {
            Product product = CreateProductFactory.createProduct();
            productRepository.save(product);
        }
    }

    public void changCostComponent(@RequestParam("id") Integer componentId,
                                   @RequestParam("fixCost") Integer fixCost) {


    }

    @GetMapping("/test")
    public void getValue() {
        Product product = productRepository.findByCompentId("22");
        System.out.println(product.getName());
    }



}
