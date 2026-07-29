package com.ekart.user.dao;

import java.sql.SQLException;
import java.util.List;

import com.ekart.user.entity.Cart;

public interface CartDao extends GenericDAO<Cart>  {
	
    public List<Cart> getAllByUid(int uid) throws SQLException;
    public int totalQuantity(List<Cart> cListObj);
    public int deleteCartByUid(int uid);
}
