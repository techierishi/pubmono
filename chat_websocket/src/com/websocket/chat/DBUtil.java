package com.websocket.chat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.DeleteDbFiles;

// H2 Database Example

public class DBUtil {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/test";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";

	public static void main(String[] args) throws Exception {
		try {
			// delete the H2 database named 'test' in the user home directory
			DeleteDbFiles.execute("~", "test", true);
			//insertWithPreparedStatement();
			createTables();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// H2 SQL Prepared Statement Example
	public static void insertWithPreparedStatement() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		PreparedStatement insertPreparedStatement = null;
		PreparedStatement selectPreparedStatement = null;

		String CreateQuery = "CREATE TABLE USER_TBL(id bigint auto_increment primary key, username varchar(255),email varchar(255),password varchar(255))";
		String InsertQuery = "INSERT INTO USER_TBL" + "(username,email,password) values" + "(?,?,?)";
		String SelectQuery = "select * from USER_TBL";
		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, "user1");
			insertPreparedStatement.setString(2, "user1@yahoo.com");
			insertPreparedStatement.setString(3, "password1");
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();


			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			ResultSet rs = selectPreparedStatement.executeQuery();
			System.out.println("H2 Database inserted through PreparedStatement");
			while (rs.next()) {
				System.out.println("Id " + rs.getInt("id") + " Name " + rs.getString("username"));
			}
			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public static void createTables() throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement createPreparedStatement = null;
		String CreateQuery = "CREATE TABLE USER_TBL(id bigint auto_increment primary key, username varchar(255),email varchar(255),password varchar(255))";

		try {
			connection.setAutoCommit(false);

			createPreparedStatement = connection.prepareStatement(CreateQuery);
			createPreparedStatement.executeUpdate();
			createPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public static void insertUser(User user) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement insertPreparedStatement = null;

		String InsertQuery = "INSERT INTO USER_TBL" + "(username,email,password) values" + "(?,?,?)";
		try {
			connection.setAutoCommit(false);

			insertPreparedStatement = connection.prepareStatement(InsertQuery);
			insertPreparedStatement.setString(1, user.getUsername());
			insertPreparedStatement.setString(2, user.getEmail());
			insertPreparedStatement.setString(3, user.getPassword());
			insertPreparedStatement.executeUpdate();
			insertPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
	}

	public static User loginUser(User user) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		User _user = null;
		String SelectQuery = "SELECT * FROM USER_TBL WHERE username=? AND password=?";
		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);
			selectPreparedStatement.setString(1, user.getUsername());
			selectPreparedStatement.setString(2, user.getPassword());

			ResultSet rs = selectPreparedStatement.executeQuery();

			while (rs.next()) {
				_user = new User();
				_user.setUid(rs.getInt("id"));
				_user.setUsername(rs.getString("username"));
				_user.setEmail(rs.getString("email"));
				_user.setPassword(rs.getString("password"));
			}
			if (null != _user)
				System.out.println(" String " + _user.toString());

			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return _user;
	}
	
	public static List<User> onlineUsers(User user) throws SQLException {
		Connection connection = getDBConnection();
		PreparedStatement selectPreparedStatement = null;
		List<User> _userList = new ArrayList<>();
		String SelectQuery = "SELECT * FROM USER_TBL";
		try {
			connection.setAutoCommit(false);

			selectPreparedStatement = connection.prepareStatement(SelectQuery);

			ResultSet rs = selectPreparedStatement.executeQuery();

			while (rs.next()) {
				User _user = new User();
				_user.setUid(rs.getInt("id"));
				_user.setUsername(rs.getString("username"));
				_user.setEmail(rs.getString("email"));
				_user.setPassword(rs.getString("password"));
				_userList.add(_user);
			}
			if (null != _userList)
				System.out.println(" String " + _userList.toString());

			selectPreparedStatement.close();

			connection.commit();
		} catch (SQLException e) {
			System.out.println("Exception Message " + e.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}

		return _userList;
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
}
