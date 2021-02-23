package com.intent.tianjian.product;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ComponentRepository extends Neo4jRepository<Component, Long> {
}
