package com.intent.tianjian.product;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

public class Component implements CountChangeCost{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer fixedCost;

    private Integer totalCost;

    @Relationship(type = "contains")
    private Set<Component> components = new HashSet<>();

    public void addComponent(Component component) {
        components.add(component);
    }

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

    public Integer getFixedCost() {
        return fixedCost;
    }

    public void setFixedCost(Integer fixedCost) {
        this.fixedCost = fixedCost;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public Set<Component> getComponents() {
        return components;
    }

    public void setComponents(Set<Component> components) {
        this.components = components;
    }

    @Override
    public Integer countChangeCost() {

        int changeCost = 0;

        if(!CollectionUtils.isEmpty(components)) {

            for(Component component : components) {
                changeCost += component.countChangeCost();
            }
        }
        totalCost = fixedCost + changeCost;
        return totalCost;
    }
}
