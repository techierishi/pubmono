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
@Table(name = "product_company")
public class ProductCompany {
   @Id
   @GeneratedValue
   private int company_id; 
   private String company_name;

    /**
     * @return the company_id
     */
    public int getCompany_id() {
        return company_id;
    }

    /**
     * @param company_id the company_id to set
     */
    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    /**
     * @return the company_name
     */
    public String getCompany_name() {
        return company_name;
    }

    /**
     * @param company_name the company_name to set
     */
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
   
   
   
   
}
