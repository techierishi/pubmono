package com.ekart.admin.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ekart.admin.dao.ProductDao;
import com.ekart.admin.dao.ProductImageDao;
import com.ekart.user.entity.Product;
import com.ekart.user.entity.ProductImage;
import com.ekart.util.DBConnection;
import com.ekart.util.Mail;

public class ProductDaoImpl implements ProductDao {

	private DBConnection conn;
	private Mail sendMail;

	public ProductDaoImpl() {
		conn = new DBConnection();
		sendMail = new Mail();
	}

	@Override
	public int insert(Product entityObj) throws ClassNotFoundException, SQLException {
		int i = 0;
		String SQL = "SELECT * FROM product WHERE productName=?";
		conn.open();
		PreparedStatement pst = conn.initStatement(SQL);
		pst.setString(1, entityObj.getProductName());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			conn.close();
			return i;
		} else {
			SQL = "INSERT INTO `product`(`productName`, `categoryName`, `productAvailability`, `productQuantity`, `productPrice`, `supplierId`, `productDetails`) VALUES(?,?,?,?,?,?,?)";
			conn.open();
			pst = conn.initStatement(SQL);
			pst.setString(1, entityObj.getProductName());
			pst.setString(2, entityObj.getCategoryName());
			pst.setString(3, "Available");
			pst.setInt(4, entityObj.getProductQuantity());
			pst.setInt(5, entityObj.getProductPrice());
			pst.setString(6, "null");
			pst.setString(7, "" + entityObj.getProductDetails());

			conn.close();
			i = pst.executeUpdate();
			// Get last id
			
			ResultSet rs2 = pst.getGeneratedKeys();
			if (rs2.next()) {
				i = rs2.getInt(1);
			}
			return i;
		}
	}

	@Override
	public int update(Product t) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public List<Product> getAll() throws ClassNotFoundException, SQLException {
		List<Product> entityList = new ArrayList<Product>();
		ProductImageDao pDao = new ProductImageDaoImpl();
		String SQL = "SELECT * FROM  product";
		conn.open();
		PreparedStatement pst = conn.initStatement(SQL);
		conn.close();
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			Product entityObj = new Product();
			entityObj.setProductId(rs.getInt("productId"));
			entityObj.setProductName(rs.getString("productName"));
			entityObj.setCategoryName(rs.getString("categoryName"));
			entityObj.setProductAvailability(rs.getString("productAvailability"));
			entityObj.setProductQuantity(rs.getInt("productQuantity"));
			entityObj.setProductPrice(rs.getInt("productPrice"));
			entityObj.setProductDetails(rs.getString("productDetails"));

			List<ProductImage> pImageList = pDao.getByProductId(entityObj);

			entityObj.setProductImages(pImageList);

			entityList.add(entityObj);
		}
		return entityList;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public Product getById(int id) throws ClassNotFoundException, SQLException {
		Product entityObj = null;
		ProductImageDao pDao = new ProductImageDaoImpl();
		String SQL = "SELECT * FROM  product WHERE productId=?";
		conn.open();
		PreparedStatement pst = conn.initStatement(SQL);
		pst.setInt(1, id);
		conn.close();
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {

			entityObj = new Product();
			entityObj.setProductId(rs.getInt("productId"));
			entityObj.setProductName(rs.getString("productName"));
			entityObj.setCategoryName(rs.getString("categoryName"));
			entityObj.setProductAvailability(rs.getString("productAvailability"));
			entityObj.setProductQuantity(rs.getInt("productQuantity"));
			entityObj.setProductPrice(rs.getInt("productPrice"));
			entityObj.setProductDetails(rs.getString("productDetails"));

			List<ProductImage> pImageList = pDao.getByProductId(entityObj);

			entityObj.setProductImages(pImageList);
		}
		return entityObj;
	}

	@Override
	public List<Product> getByCategory() throws SQLException {
		return null;
	}

}
