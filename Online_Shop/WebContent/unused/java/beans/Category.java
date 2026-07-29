/**
 * 
 */
package com.ekart.user.entity;

import java.util.ArrayList;

/**
 * @author Rishikesh
 *
 */
public class Category {
	
	public ArrayList<String> getCategories() {
		return categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public ArrayList<String> categories=new ArrayList<String>();

}
