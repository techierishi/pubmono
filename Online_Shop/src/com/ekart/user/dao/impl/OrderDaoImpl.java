package com.ekart.user.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ekart.user.dao.OrderDao;
import com.ekart.user.entity.Card;
import com.ekart.user.entity.Cart;
import com.ekart.user.entity.LineItem;
import com.ekart.user.entity.Order;
import com.ekart.util.DBConnection;

public class OrderDaoImpl implements OrderDao {
	private DBConnection conn;

	public OrderDaoImpl() {
		super();
		this.conn = new DBConnection();
	}

	@Override
	public int insert(Order entityObj) throws ClassNotFoundException, SQLException {
		conn.open();
		PreparedStatement pst = null;

		String SQL = "INSERT INTO  `order` (`customerId` ,`orderStatus` ,`paymentType`) VALUES (?,?,?);";
		conn.open();
		pst = conn.initStatement(SQL);
		pst.setInt(1, entityObj.getCustomerId());
		pst.setString(2, entityObj.getOrderStatus());
		pst.setString(3, entityObj.getPaymentType());
		conn.close();
		int i = 0;
		pst.executeUpdate();
		// Get last id
		ResultSet rs2 = pst.getGeneratedKeys();
		if (rs2.next()) {
			i = rs2.getInt(1);
		}

		return i;
	}

	@Override
	public int update(Order t) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Order> getAll() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Order getById(int id) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertCard(Card card) {
		PreparedStatement pst = null;
		int i = 0;
		
		try {
			String SQL = "INSERT INTO  `card` (`uid` ,`card_type` ,`card_number` ,`card_cvv`) VALUES (?,?,?,?);";
			conn.open();
			pst = conn.initStatement(SQL);
			pst.setInt(1, card.getUid());
			pst.setString(2, card.getCard_type());
			pst.setString(3, card.getCart_number());
			pst.setString(3, card.getCart_cvv());

			conn.close();
			i = pst.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return i;
	}

	@Override
	public boolean insertLineItem(Order order, List<Cart> cartItems) {

		boolean inserted = true;

		try {
			conn.open();
			PreparedStatement pst = null;

			for (Cart cart : cartItems) {

				String SQL = "INSERT INTO  `line_item` (`oid` ,`pid` ,`qty`) VALUES (?,?,?);";
				conn.open();
				pst = conn.initStatement(SQL);
				pst.setInt(1, order.getOrderId());
				pst.setInt(2, cart.getPid());
				pst.setInt(3, cart.getQuantity());

				conn.close();
				int i = pst.executeUpdate();

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inserted;
	}

}
