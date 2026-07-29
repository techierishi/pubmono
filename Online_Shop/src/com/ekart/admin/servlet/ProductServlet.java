package com.ekart.admin.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import com.ekart.admin.dao.CategoryDao;
import com.ekart.admin.dao.ProductDao;
import com.ekart.admin.dao.ProductImageDao;
import com.ekart.admin.dao.impl.CategoryDaoImpl;
import com.ekart.admin.dao.impl.ProductDaoImpl;
import com.ekart.admin.dao.impl.ProductImageDaoImpl;
import com.ekart.user.entity.Category;
import com.ekart.user.entity.FileMeta;
import com.ekart.user.entity.Product;
import com.ekart.user.entity.ProductImage;
import com.ekart.util.Const;
import com.ekart.util.MultipartRequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/backend/product/*")
@MultipartConfig
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		String pathInfo = request.getPathInfo();
		if (null != pathInfo && pathInfo.equalsIgnoreCase("/add")) {

			try {
				CategoryDao CategoryDao = new CategoryDaoImpl();
				List<Category> categories = CategoryDao.getAll();
				request.setAttribute("category_list", categories);
				request.getRequestDispatcher(Const.BAKEND + "product_add.jsp").forward(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		if (null != pathInfo && pathInfo.equalsIgnoreCase("/list")) {

			try {
				CategoryDao CategoryDao = new CategoryDaoImpl();
				List<Category> categories = CategoryDao.getAll();
				request.setAttribute("category_list", categories);
				request.getRequestDispatcher(Const.BAKEND + "category_list.jsp").forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ProductDao entityService = new ProductDaoImpl();
		ProductImageDao pDaoImg= new ProductImageDaoImpl();

		try {

			String product_name = request.getParameter("product_name");
			String product_price = request.getParameter("product_price");
			String product_qty = request.getParameter("product_qty");
			String product_category = request.getParameter("product_category");
			String product_img_json = request.getParameter("product_img_json");

			JSONArray jArr = new JSONArray(product_img_json);
			List<ProductImage> productImages = new ArrayList<ProductImage>();

			int length = jArr.length();
			if (null != jArr && length > 0) {
				for (int i = 0; i < length; i++) {
					ProductImage productImage = new ProductImage();
					productImage.setImageName(jArr.getJSONObject(i).getString("fileName"));
					productImages.add(productImage);
				}
			}

			Product product = new Product();
			product.setProductName(product_name);
			product.setProductPrice(Integer.parseInt(product_price));
			product.setProductQuantity(Integer.parseInt(product_qty));
			product.setCategoryName(product_category);
			product.setProductName(product_name);
			int ins = entityService.insert(product);
			
			product.setProductId(ins);
			pDaoImg.insert(product, productImages);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.getSession().setAttribute("message", "Product Added successfully!");
		response.sendRedirect(request.getContextPath() + "/backend/product/add");

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		if (null != pathInfo && pathInfo.equalsIgnoreCase("/add")) {
			addProduct(request, response);
		} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/uploadimg")) {
			List<FileMeta> files = new LinkedList<FileMeta>();

			// 1. Upload File Using Apache FileUpload
			files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));

			for (FileMeta filem : files) {
				MultipartRequestHandler.uploadFileNow(request, filem.getFileName(), filem.getContent());
			}
			// Remove some files
			while (files.size() > 20) {
				files.remove(0);
			}

			System.out.println("Files : " + files);

			// 2. Set response type to json
			response.setContentType("application/json");

			// 3. Convert List<FileMeta> into JSON format
			ObjectMapper mapper = new ObjectMapper();

			// 4. Send result to client
			mapper.writeValue(response.getOutputStream(), files);
		}
	}

}
