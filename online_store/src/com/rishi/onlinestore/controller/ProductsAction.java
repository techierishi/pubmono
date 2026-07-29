package com.rishi.onlinestore.controller;

import com.google.gson.Gson;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.rishi.onlinestore.model.Product;
import com.rishi.onlinestore.service.ProductService;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsAction extends ActionSupport {

    private HttpServletRequest request;

    private HttpSession session;

    private ServletContext application;
    private List<Product> products;
    private Map<String, String> params = new HashMap<String, String>();

    private Product product;
    private InputStream productJson;

   

    public String list() throws Exception {
        this.request = ServletActionContext.getRequest();
        this.session = this.request.getSession();

        String ret = "";

        ProductService productService = new ProductService();

        if (true) {
            if (null != productService.getListOfProducts() && !productService.getListOfProducts().isEmpty()) {
                setProducts(productService.getListOfProducts());
            }
            ret = "products";
        } else {
            ret = "products";
        }

        return ret;

    }

    public String productsJson() throws Exception {
        this.request = ServletActionContext.getRequest();
        this.session = this.request.getSession();

        String ret = "";

        ProductService productService = new ProductService();

        if (true) {
            if (null != productService.getListOfProducts() && !productService.getListOfProducts().isEmpty()) {
                Gson gson = new Gson();
                String json = gson.toJson(productService.getListOfProducts());


                productJson = new StringBufferInputStream(json);
            }
            ret = SUCCESS;
        } else {
            ret = SUCCESS;
        }

        return ret;

    }

    public String detail() throws Exception {
        this.request = ServletActionContext.getRequest();
        this.session = this.request.getSession();

        String ret = "";

        ProductService productService = new ProductService();

        if (true) {
            String product_id = this.params.get("product_id");
            Product _product = productService.getProductDetail(product_id);
            if (null != _product) {
                this.product = _product;
            }
            ret = "product";
        } else {
            ret = "product";
        }

        return ret;

    }

    /**
     * @return the products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * @param products the products to set
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * @return the productJson
     */
    public InputStream getProductJson() {
        return productJson;
    }

  

}
