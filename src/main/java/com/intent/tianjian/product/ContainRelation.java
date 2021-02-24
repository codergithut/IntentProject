package com.intent.tianjian.product;


import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class ContainRelation {
    @Id
    @GeneratedValue
    private Long relationshipId;

    @Property
    private Integer weight;

    @TargetNode
    private Component component;

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }


}
