package com.ekart.admin.dao;

import java.sql.SQLException;
import java.util.List;

import com.ekart.user.dao.GenericDAO;
import com.ekart.user.entity.Category;
import com.ekart.user.entity.Product;

public interface ProductDao extends GenericDAO<Product>{
    public List<Product> getByCategory() throws SQLException;

}
