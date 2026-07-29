package com.ekart.user.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ekart.user.dao.CustomerDao;
import com.ekart.user.entity.Customer;
import com.ekart.util.DBConnection;

public class CustomerDaoImpl implements CustomerDao {
	private DBConnection conn;

	public CustomerDaoImpl() {
		super();
		this.conn = new DBConnection();
	}

	@Override
	public int insert(Customer entityObj) throws ClassNotFoundException, SQLException {
		int i = 0;
		String SQL = "SELECT * FROM customer WHERE customerEmail=?";
		conn.open();
		PreparedStatement pst = conn.initStatement(SQL);
		pst.setString(1, entityObj.getEmail());
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			conn.close();
			return i;
		} else {
			SQL = "INSERT INTO  `customer` (`customerName` ,`customerPhone` ,`customerEmail` ,`customerAddress` ,`customerState` ,`customerCity` ,`customerZip` ,`customerPassword`) VALUES (?,?,?,?,?, ?,?,?);";
			conn.open();
			pst = conn.initStatement(SQL);
			pst.setString(1, entityObj.getName());
			pst.setInt(2, Integer.parseInt(entityObj.getPhone()));
			pst.setString(3, entityObj.getEmail());
			pst.setString(4, entityObj.getAddress());
			pst.setString(5, entityObj.getState());
			pst.setString(6, entityObj.getCity());
			pst.setString(7, entityObj.getZip());
			pst.setString(8, "" + entityObj.getPassword());
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
	public int update(Customer t) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Customer> getAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Customer getById(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer login(String username, String password) {
		Customer cObj = null;

		try {
			String SQL = "SELECT * FROM customer WHERE customerEmail=? AND customerPassword=?";
			conn.open();
			PreparedStatement pst = conn.initStatement(SQL);
			pst.setString(1, username);
			pst.setString(2, password);

			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				cObj = new Customer();
				cObj.setName(rs.getString("customerName"));
				cObj.setPhone(rs.getString("customerPhone"));
				cObj.setCity(rs.getString("customerCity"));
				cObj.setState(rs.getString("customerState"));
				cObj.setZip(rs.getString("customerZip"));
				cObj.setEmail(rs.getString("customerEmail"));
				cObj.setId(rs.getInt("customerId"));
			}
			conn.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return cObj;
	}

}
