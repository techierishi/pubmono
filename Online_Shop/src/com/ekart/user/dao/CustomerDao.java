package com.ekart.user.dao;

import com.ekart.user.entity.Customer;

public interface CustomerDao extends GenericDAO<Customer> {

	public Customer login(String username,String password);
}
