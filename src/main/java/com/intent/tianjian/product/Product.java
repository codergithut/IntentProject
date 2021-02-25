package com.intent.tianjian.product;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Node
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer totalCost;

    @Relationship(type="component")
    private Set<ComponentRelation> componentRelations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Set<ComponentRelation> getComponentRelations() {
        return componentRelations;
    }

    public void setComponentRelations(Set<ComponentRelation> componentRelations) {
        this.componentRelations = componentRelations;
    }

    public void addComponentRelation(Component component) {
        ComponentRelation componentRelation = new ComponentRelation();
        componentRelation.setWeight(RandomUtil.randomInt(3) + 1);
        componentRelation.setComponent(component);
        componentRelations.add(componentRelation);
    }

    public Integer countTotalCost() {
        totalCost = 0;
        if(CollectionUtils.isEmpty(componentRelations)) {
            return 0;
        }
        for(ComponentRelation componentRelation : componentRelations) {
            totalCost += componentRelation.getComponent().countChangeCost() * componentRelation.getWeight();
        }
        return totalCost;
    }


}
