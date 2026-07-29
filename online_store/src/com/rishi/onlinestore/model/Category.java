package com.rishi.onlinestore.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {

    @Id
    @GeneratedValue
    private Long category_id;
    private String category_name;
    
    @ManyToMany(mappedBy="categories")
    private Set<Product> product = new HashSet<Product>();
    
    public Category() {
    }

    /**
     * @return the category_id
     */
    public Long getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id the category_id to set
     */
    public void setCategory_id(Long category_id) {
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

    public Set<Product> getProduct() {
        return product;
    }

    public void setProduct(Set<Product> products) {
        this.product = products;
    }



}
