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
@Table(name = "administrators")
public class Administrators {

    @Id
    @GeneratedValue
    private String admin_id;
    private String email;
    private String password;

    /**
     * @return the admin_id
     */
    public String getAdmin_id() {
        return admin_id;
    }

    /**
     * @param admin_id the admin_id to set
     */
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
