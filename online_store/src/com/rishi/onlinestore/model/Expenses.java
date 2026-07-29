package com.rishi.onlinestore.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EXPENSES")
public class Expenses implements Serializable {

    @Id
    @GeneratedValue
    private Long expenses_id;
    private Double price;
    private Long product_id;
    private String product_name;
    private Date purchase_date;

    public Expenses() {
    }

    /**
     * @return the expenses_id
     */
    public Long getExpenses_id() {
        return expenses_id;
    }

    /**
     * @param expenses_id the expenses_id to set
     */
    public void setExpenses_id(Long expenses_id) {
        this.expenses_id = expenses_id;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the product_id
     */
    public Long getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    /**
     * @return the product_name
     */
    public String getProduct_name() {
        return product_name;
    }

    /**
     * @param product_name the product_name to set
     */
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    /**
     * @return the purchase_date
     */
    public Date getPurchase_date() {
        return purchase_date;
    }

    /**
     * @param purchase_date the purchase_date to set
     */
    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }
    
    
    

}
