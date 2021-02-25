package com.intent.tianjian.mysql.eo;

public class ComponentEo {

    private String id;

    private String componentName;

    private Integer fixedCost;

    private Integer totalCost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
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
}
