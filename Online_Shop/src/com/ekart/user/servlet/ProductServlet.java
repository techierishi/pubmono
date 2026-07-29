package com.ekart.user.servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ekart.admin.dao.ProductDao;
import com.ekart.admin.dao.impl.ProductDaoImpl;
import com.ekart.user.entity.FileMeta;
import com.ekart.user.entity.Product;
import com.ekart.util.Const;
import com.ekart.util.MultipartRequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/product/*")
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

		if (null != pathInfo && pathInfo.equalsIgnoreCase("/list")) {

			try {
				ProductDao ProductDao = new ProductDaoImpl();
				List<Product> products = ProductDao.getAll();
				request.setAttribute("product_list", products);
				request.getRequestDispatcher(Const.SITE + "products.jsp").forward(request, response);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (null != pathInfo && pathInfo.equalsIgnoreCase("/detail")) {

			try {
				ProductDao ProductDao = new ProductDaoImpl();

				String productId = request.getParameter("pid");

				Product product = ProductDao.getById(Integer.parseInt(productId));
				request.setAttribute("product", product);
				request.getRequestDispatcher(Const.SITE + "single.jsp").forward(request, response);

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

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

	private void addProduct(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

}
