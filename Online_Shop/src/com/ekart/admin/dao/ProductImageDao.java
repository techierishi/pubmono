package com.ekart.admin.dao;

import java.sql.SQLException;
import java.util.List;

import com.ekart.user.dao.GenericDAO;
import com.ekart.user.entity.Product;
import com.ekart.user.entity.ProductImage;

public interface ProductImageDao extends GenericDAO<ProductImage>{
    public List<ProductImage> getByProductId(Product product) throws ClassNotFoundException, SQLException;
    public int insert(Product product, List<ProductImage> productImages) throws SQLException;

}
