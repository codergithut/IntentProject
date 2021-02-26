package com.intent.tianjian.mysql.curd;

import com.intent.tianjian.mysql.eo.ComponentEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComponentCurd extends CrudRepository<ComponentEo,String> {
    List<ComponentEo> findByProductId(String productId);
}
