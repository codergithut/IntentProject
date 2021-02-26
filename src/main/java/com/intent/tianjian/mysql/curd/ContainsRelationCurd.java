package com.intent.tianjian.mysql.curd;

import com.intent.tianjian.mysql.eo.ContainsRelationEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContainsRelationCurd extends CrudRepository<ContainsRelationEo,String> {
    List<ContainsRelationEo> findByProductId(String productId);
}
