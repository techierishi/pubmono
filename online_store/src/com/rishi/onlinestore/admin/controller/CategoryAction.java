package com.rishi.onlinestore.admin.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.opensymphony.xwork2.ActionSupport;
import com.rishi.onlinestore.admin.service.CategoryService;
import com.rishi.onlinestore.lib.DBG;
import com.rishi.onlinestore.model.Category;
import java.util.List;

public class CategoryAction extends ActionSupport {
    private HttpServletRequest request;
    private HttpSession session;
    private ServletContext application;
    private Category category;
    private List<Category> categoryList;
    

    public String categoryadd() throws Exception {
        
        CategoryService categoryService = new CategoryService();
        
        String ret = "";
        
        DBG.d("categoryadd", ""+categoryList);
        
        if(null != category){
            ret = "redirect";
            boolean result = categoryService.categoryadd(category);
        }else{
            ret = SUCCESS;
        }
        
        categoryList = categoryService.getCategoryList();
        setCategoryList(categoryList);
      
        return ret;

    }
    
    public String categorylist() throws Exception {
        
        String ret = "";
        ret = SUCCESS;
        
        return ret;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the categoryList
     */
    public List<Category> getCategoryList() {
        return categoryList;
    }

    /**
     * @param categoryList the categoryList to set
     */
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

}
