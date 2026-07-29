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
@Table(name = "sub_category")
public class SubCategory {
@Id
@GeneratedValue
private int subcategory_id;
private String sub_category_name;
private String category_name;

    /**
     * @return the subcategory_id
     */
    public int getSubcategory_id() {
        return subcategory_id;
    }

    /**
     * @param subcategory_id the subcategory_id to set
     */
    public void setSubcategory_id(int subcategory_id) {
        this.subcategory_id = subcategory_id;
    }

    /**
     * @return the sub_category_name
     */
    public String getSub_category_name() {
        return sub_category_name;
    }

    /**
     * @param sub_category_name the sub_category_name to set
     */
    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
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
