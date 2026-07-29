package com.rishi.onlinestore.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER_VALUES")
public class OrderValues implements Serializable {

    @Id
    @GeneratedValue
    private Long order_values_id;
    private Long product_id;
    private Long quantity;


    public OrderValues() {
    }

    public Long getOrder_values_id() {
        return order_values_id;
    }

    public void setOrder_values_id(Long order_values_id) {
        this.order_values_id = order_values_id;
    }
    
    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

   

}
