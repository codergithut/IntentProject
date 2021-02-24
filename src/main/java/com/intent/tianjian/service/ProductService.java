package com.intent.tianjian.service;

import com.alibaba.fastjson.JSONObject;
import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.ComponentRepository;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComponentRepository componentRepository;

    public Product getProductByComponentId(Long id){
        Product product = productRepository.findByComponentId(22L);
        Optional<Product> productOpt = productRepository.findById(product.getId());
        if(productOpt.isPresent()){
            return productOpt.get();
        }
        return null;
    }

    public Product changeComponentCost(Long id, Integer fixedCost) {
        Optional<Component> component = componentRepository.findById(id);
        if(!component.isPresent()) {
            return null;
        }
        Component saveComponent = component.get();
        saveComponent.setFixedCost(fixedCost);
        componentRepository.save(saveComponent);

        Product product = getProductByComponentId(id);
        product.countTotalCost();
        productRepository.save(product);
        return product;

    }

    public boolean createProductByCountParam(Integer count) {
        productRepository.deleteAll();
        for(int i = 0; i < count; i++) {
            Product product = CreateProductFactory.createProduct();
            productRepository.save(product);
        }
        return true;
    }
}
