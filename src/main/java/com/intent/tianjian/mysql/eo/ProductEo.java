package com.intent.tianjian.mysql.eo;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductEo {

    @Id
    private String id;

    private String productName;

    private Integer totalCost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
