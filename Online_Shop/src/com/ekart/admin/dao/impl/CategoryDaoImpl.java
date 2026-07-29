package com.ekart.admin.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ekart.admin.dao.CategoryDao;
import com.ekart.admin.entity.Admin;
import com.ekart.user.entity.Category;
import com.ekart.util.DBConnection;
import com.ekart.util.Mail;

public class CategoryDaoImpl implements CategoryDao{

	  private DBConnection conn;
	  private Mail sendMail;
	  public CategoryDaoImpl() {
	        conn = new DBConnection();
	        sendMail = new Mail();
	    }
	@Override
	public int insert(Category category) throws ClassNotFoundException, SQLException {
        int i=0;
        String SQL = "SELECT * FROM category WHERE categoryName=?";
        conn.open();
        PreparedStatement pst = conn.initStatement(SQL);
        pst.setString(1, category.getCategory_name());
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            conn.close();
            return i;
        }
        else{
            SQL = "INSERT INTO category(categoryName, categoryDetails) VALUES(?,?)";
            conn.open();
            pst = conn.initStatement(SQL);
            pst.setString(1, category.getCategory_name());
            pst.setString(2, category.getCategory_detail());
            conn.close();
            i = pst.executeUpdate();
            return i;
        }
	}

	@Override
	public int update(Category t) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public List<Category> getAll() throws ClassNotFoundException, SQLException {
		 List<Category> entityList = new ArrayList<Category>();
	        String SQL = "SELECT * FROM  category";
	        conn.open();
	        PreparedStatement pst = conn.initStatement(SQL);
	        conn.close();
	        ResultSet rs = pst.executeQuery();
	        while(rs.next()){
	            Category entityObj = new Category();
	            entityObj.setCategory_name(rs.getString("categoryName"));
	            entityObj.setCategory_detail(rs.getString("categoryDetails"));
	            entityList.add(entityObj);
	        }
	        return entityList;
	}

	@Override
	public int delete(int id) throws ClassNotFoundException, SQLException {
		return 0;
	}

	@Override
	public Category getById(int id) throws ClassNotFoundException, SQLException {
		return null;
	}

	
}
