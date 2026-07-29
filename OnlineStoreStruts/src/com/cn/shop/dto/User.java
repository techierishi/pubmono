package com.teknikstore.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String email;
    private String password;
    private String registeredOn;

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "pass")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    @Column(name = "email")
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
     * @return the registeredOn
     */
     @Column(name = "registeredOn")
    public String getRegisteredOn() {
        return registeredOn;
    }

    /**
     * @param registeredOn the registeredOn to set
     */
    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}
