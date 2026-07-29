/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teknikstore.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "expenses")
public class Expenses {
    
    
    @Id
    @GeneratedValue
    private int expenses_id;
    private int product_id;    
    private String product_name;
    private String price;
    private Date purchase_date;

    /**
     * @return the expenses_id
     */
    public int getExpenses_id() {
        return expenses_id;
    }

    /**
     * @param expenses_id the expenses_id to set
     */
    public void setExpenses_id(int expenses_id) {
        this.expenses_id = expenses_id;
    }

    /**
     * @return the product_id
     */
    public int getProduct_id() {
        return product_id;
    }

    /**
     * @param product_id the product_id to set
     */
    public void setProduct_id(int product_id) {
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
     * @return the price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = price;
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
