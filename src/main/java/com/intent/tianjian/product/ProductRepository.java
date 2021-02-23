package com.intent.tianjian.product;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;


public interface ProductRepository extends Neo4jRepository<Product, Long> {
    Product findByName(String name);

    @Query("MATCH (t:Product) -[*]-> (c:Component) where id(c)={0} return t")
    Product findByCompentId(String id);
}
