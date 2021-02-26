package com.intent.tianjian.service;

import com.intent.tianjian.mock.CreateProductFactory;
import com.intent.tianjian.mysql.BeanConvertMysqlService;
import com.intent.tianjian.mysql.eo.ProductEo;
import com.intent.tianjian.product.Component;
import com.intent.tianjian.product.ComponentRepository;
import com.intent.tianjian.product.Product;
import com.intent.tianjian.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private BeanConvertMysqlService beanConvertMysqlService;

    public Product getProductByComponentId(Long id){
        Product product = productRepository.findByComponentId(id);
        product = productRepository.findById(product.getId()).get();
        return product;
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
        initData();
        beanConvertMysqlService.initDataBase();

        for(int i = 0; i < count; i++) {
            String id = UUID.randomUUID().toString();
            Product product = CreateProductFactory.createProduct(2, 2);
            product.setTotalCost(product.countTotalCost());
            productRepository.save(product);
            beanConvertMysqlService.saveProductToMysql(product, id);
            Product product1 = beanConvertMysqlService.getProductByProductId(id);
            System.out.print(product);
        }



        return true;
    }


    public void initData() {
        productRepository.deleteAll();
        componentRepository.deleteAll();
    }

    public boolean clearData() {
        return true;
    }
}
