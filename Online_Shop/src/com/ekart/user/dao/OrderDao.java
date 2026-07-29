package com.ekart.user.dao;

import java.util.List;

import com.ekart.user.entity.Card;
import com.ekart.user.entity.Cart;
import com.ekart.user.entity.Order;

public interface OrderDao extends GenericDAO<Order> {

	public int insertCard(Card card);
	public boolean insertLineItem(Order order,List<Cart> cartItems);
}
