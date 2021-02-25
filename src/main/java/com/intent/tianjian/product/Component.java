package com.intent.tianjian.product;

import cn.hutool.core.util.RandomUtil;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Node
public class Component implements CountChangeCost{

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer fixedCost;

    private Integer totalCost;

    private Integer depNum;

    @Relationship(type="contain")
    private Set<ContainRelation> containRelations = new HashSet();

    public Long getId() {
        return id;
    }

    public Set<ContainRelation> getContainRelations() {
        return containRelations;
    }

    public void setContainRelations(Set<ContainRelation> containRelations) {
        this.containRelations = containRelations;
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

    public void addContainsRelation(Component component) {
        ContainRelation containRelation = new ContainRelation();
        containRelation.setComponent(component);
        containRelation.setWeight(RandomUtil.randomInt(6));
        containRelations.add(containRelation);

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

    public Integer getDepNum() {
        return depNum;
    }

    public void setDepNum(Integer depNum) {
        this.depNum = depNum;
    }

    @Override
    public Integer countChangeCost() {

        int changeCost = 0;

        if(!CollectionUtils.isEmpty(containRelations)) {

            for(ContainRelation containRelation : containRelations) {
                changeCost += containRelation.getComponent().countChangeCost() * containRelation.getWeight();
            }
        }
        totalCost = fixedCost + changeCost;
        return totalCost;
    }
}
