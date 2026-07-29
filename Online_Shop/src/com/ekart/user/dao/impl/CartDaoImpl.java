package com.ekart.user.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ekart.user.dao.CartDao;
import com.ekart.user.entity.Cart;
import com.ekart.user.entity.Customer;
import com.ekart.util.DBConnection;

public class CartDaoImpl implements CartDao {
	private DBConnection conn;

	public CartDaoImpl() {
		super();
		this.conn = new DBConnection();
	}

	@Override
	public int insert(Cart t) throws ClassNotFoundException, SQLException {
		int i = 0;
		String SQL = "";
		conn.open();
		PreparedStatement pst = null;
		SQL = "INSERT INTO  `cart` (`pid` ,`uid` ,`quantity` ,`productName`) VALUES (?,?,?,?);";
		pst = conn.initStatement(SQL);
		pst.setInt(1, t.getPid());
		pst.setInt(2, t.getUid());
		pst.setInt(3, t.getQuantity());
		pst.setString(4, t.getProductName());
		conn.close();
		i = pst.executeUpdate();
		// Get last id
		ResultSet rs2 = pst.getGeneratedKeys();
		if (rs2.next()) {
			i = rs2.getInt(1);
		}
		return i;
	}

	@Override
	public int update(Cart t) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Cart> getAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public Cart getById(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cart> getAllByUid(int uid) throws SQLException {
		List<Cart> cListObj = new ArrayList<Cart>();

		try {
			String SQL = "SELECT * FROM cart WHERE uid=?";
			conn.open();
			PreparedStatement pst = conn.initStatement(SQL);
			pst.setInt(1, uid);

			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Cart cObj = new Cart();
				cObj.setProductName(rs.getString("productName"));
				cObj.setQuantity(rs.getInt("quantity"));
				cObj.setUid(rs.getInt("uid"));
				cObj.setPid(rs.getInt("pid"));
				cObj.setId(rs.getInt("id"));
				cListObj.add(cObj);
			}
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cListObj;
	}

	public int totalQuantity(List<Cart> cListObj) {
		int qty = 0;

		for (Cart cObj : cListObj) {
			qty += cObj.getQuantity();
		}

		return qty;

	}

	@Override
	public int deleteCartByUid(int uid) {
		int del = 0;
		try {
			// delete all
			conn.open();
			PreparedStatement pst = null;
			String SQL = "DELETE FROM `cart` WHERE uid=?;";
			pst = conn.initStatement(SQL);
			pst.setInt(1, uid);
			del = pst.executeUpdate();
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return del;
	}
}
