/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teknikstore.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue
    private String category_id;
    private String category_name;

    /**
     * @return the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id the category_id to set
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return the category_name
     */
    public String getCategory_name() {
        return category_name;
    }

    /**
     * @param category_name the category_name to set
     */
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    
    
}
