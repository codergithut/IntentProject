package com.intent.tianjian.mysql.curd;

import com.intent.tianjian.mysql.eo.ProductEo;
import org.springframework.data.repository.CrudRepository;

public interface ProductCurd extends CrudRepository<ProductEo,String> {
}
