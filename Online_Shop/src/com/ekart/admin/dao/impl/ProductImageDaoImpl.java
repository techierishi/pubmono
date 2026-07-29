package com.ekart.admin.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ekart.admin.dao.ProductImageDao;
import com.ekart.user.entity.Category;
import com.ekart.user.entity.Product;
import com.ekart.user.entity.ProductImage;
import com.ekart.util.DBConnection;
import com.ekart.util.Mail;

public class ProductImageDaoImpl implements ProductImageDao {
	private DBConnection conn;
	private Mail sendMail;

	public ProductImageDaoImpl() {
		conn = new DBConnection();
		sendMail = new Mail();
	}

	@Override
	public int insert(ProductImage t) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public int update(ProductImage t) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public List<ProductImage> getAll() throws ClassNotFoundException, SQLException {
		return null;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public ProductImage getById(int id) throws ClassNotFoundException, SQLException {
		return null;
	}

	@Override
	public List<ProductImage> getByProductId(Product product) throws ClassNotFoundException, SQLException {
		List<ProductImage> entityList = new ArrayList<ProductImage>();
		String SQL = "SELECT * FROM  product_image WHERE pId=?";
		conn.open();
		PreparedStatement pst = conn.initStatement(SQL);
		conn.close();
		pst.setInt(1, product.getProductId());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			ProductImage entityObj = new ProductImage();
			entityObj.setPid(rs.getString("pId"));
			entityObj.setImageName(rs.getString("imageName"));
			entityList.add(entityObj);
		}
		return entityList;
	}

	@Override
	public int insert(Product product, List<ProductImage> productImages) throws SQLException {
		int i = 0;
		String SQL = "INSERT INTO product_image(pId, imageName) VALUES(?,?)";
		if (null != productImages && !productImages.isEmpty()) {
			for (ProductImage productImage : productImages) {
				try {
					conn.open();
					PreparedStatement pst = conn.initStatement(SQL);
					pst = conn.initStatement(SQL);
					pst.setInt(1, product.getProductId());
					pst.setString(2, productImage.getImageName());
					conn.close();
					i = pst.executeUpdate();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return i;

	}

}
