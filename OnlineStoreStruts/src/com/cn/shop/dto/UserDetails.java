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
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue
    private int user_detail_id;
    private int user_id;
    private String username;
    private String mobile_no;
    private String address;
    private String gender;
    private String user_image;

    /**
     * @return the user_detail_id
     */
    public int getUser_detail_id() {
        return user_detail_id;
    }

    /**
     * @param user_detail_id the user_detail_id to set
     */
    public void setUser_detail_id(int user_detail_id) {
        this.user_detail_id = user_detail_id;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the mobile_no
     */
    public String getMobile_no() {
        return mobile_no;
    }

    /**
     * @param mobile_no the mobile_no to set
     */
    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
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
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the user_image
     */
    public String getUser_image() {
        return user_image;
    }

    /**
     * @param user_image the user_image to set
     */
    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
    
    
    
    
}
