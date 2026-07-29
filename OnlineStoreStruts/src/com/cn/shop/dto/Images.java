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
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue
    private int image_id;
    private String image_name;
    private String product_name;

    /**
     * @return the image_id
     */
    public int getImage_id() {
        return image_id;
    }

    /**
     * @param image_id the image_id to set
     */
    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    /**
     * @return the image_name
     */
    public String getImage_name() {
        return image_name;
    }

    /**
     * @param image_name the image_name to set
     */
    public void setImage_name(String image_name) {
        this.image_name = image_name;
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
    
    
    
}
