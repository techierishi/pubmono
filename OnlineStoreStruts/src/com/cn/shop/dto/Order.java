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
@Table(name = "order")
public class Order {
@Id
@GeneratedValue
private int order_id;
private int  user_id;
private String status;
private String shippers_name;
private String address;
private String mobile_number;
private String shippers_email;
private String ordered_on;
private String total_order_price;

    /**
     * @return the order_id
     */
    public int getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id the order_id to set
     */
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    /**
     * @return the user_id
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the shippers_name
     */
    public String getShippers_name() {
        return shippers_name;
    }

    /**
     * @param shippers_name the shippers_name to set
     */
    public void setShippers_name(String shippers_name) {
        this.shippers_name = shippers_name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the mobile_number
     */
    public String getMobile_number() {
        return mobile_number;
    }

    /**
     * @param mobile_number the mobile_number to set
     */
    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    /**
     * @return the shippers_email
     */
    public String getShippers_email() {
        return shippers_email;
    }

    /**
     * @param shippers_email the shippers_email to set
     */
    public void setShippers_email(String shippers_email) {
        this.shippers_email = shippers_email;
    }

    /**
     * @return the ordered_on
     */
    public String getOrdered_on() {
        return ordered_on;
    }

    /**
     * @param ordered_on the ordered_on to set
     */
    public void setOrdered_on(String ordered_on) {
        this.ordered_on = ordered_on;
    }

    /**
     * @return the total_order_price
     */
    public String getTotal_order_price() {
        return total_order_price;
    }

    /**
     * @param total_order_price the total_order_price to set
     */
    public void setTotal_order_price(String total_order_price) {
        this.total_order_price = total_order_price;
    }







}
