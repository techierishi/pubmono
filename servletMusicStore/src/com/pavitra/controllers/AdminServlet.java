package com.pavitra.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavitra.business.Download;
import com.pavitra.business.FileMeta;
import com.pavitra.business.Invoice;
import com.pavitra.business.Product;
import com.pavitra.data.DownloadDB;
import com.pavitra.data.InvoiceDB;
import com.pavitra.data.ProductDB;
import com.pavitra.util.MultipartRequestHandler;

/**
 * Controls the restricted, admin area of the website.
 * 
 * @author Rishikesh Jha
 */
@WebServlet(name = "AdminServlet", urlPatterns = { "/admin/*" })
@MultipartConfig
public class AdminServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String url = "/admin_panel/index.jsp";

		System.out.println(requestURI);

		if (requestURI.endsWith("/processInvoices")) {
			url = processInvoices(request, response);
		} else if (requestURI.endsWith("/displayLogs")) {
			url = displayLogs(request, response);
		} else if (requestURI.endsWith("/displayInvoice")) {
			url = displayInvoice(request, response);
		} else if (requestURI.endsWith("/add_product")) {
			url = addProduct(request, response);
		}

		getServletContext().getRequestDispatcher(url).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		String url = "/admin_panel/index.jsp";

		if (requestURI.endsWith("/processInvoice")) {
			url = processInvoice(request, response);
			getServletContext().getRequestDispatcher(url).forward(request, response);

		} else if (requestURI.endsWith("/processInvoices")) {
			url = processInvoices(request, response);
			getServletContext().getRequestDispatcher(url).forward(request, response);

		} else if (requestURI.endsWith("/add_product_file")) {

			saveProduct(request);
			
			response.sendRedirect(getServletContext().getContextPath()+"/catalog");
			

		}

	}

	private void saveProduct(HttpServletRequest request) throws IOException, ServletException, FileNotFoundException {
		
		String product_name = request.getParameter("product_name");
		String product_price = request.getParameter("product_price");
		String product_album = request.getParameter("product_album");
		String product_descr = request.getParameter("product_descr");
		String product_code = request.getParameter("product_code");
		String product_genre = request.getParameter("product_genre");
		String product_artist = request.getParameter("product_artist");

		Product pObj = new Product();
		pObj.setAlbumInformation(product_name);
		pObj.setCode(product_code);
		pObj.setCsAlbumTitles(product_album);
		pObj.setDescription(product_descr);
		pObj.setPrice(BigDecimal.valueOf(Double.parseDouble(product_price)));
		pObj.setSongArtist(product_artist);
		pObj.setSongGenre(product_genre);

		System.out.println("add_product_file..... --- " + pObj.toString());

		/*
		 * } catch (FileUploadException e) { e.printStackTrace(); }
		 */

		List<FileMeta> files = new LinkedList<FileMeta>();
		System.out.println("add_product_file.......");

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
		
		pObj.setSongImg(files.get(0).getFileName());
		pObj.setSongMp3(files.get(1).getFileName());
		
		ProductDB.insert(pObj);
	}

	private String processInvoices(HttpServletRequest request, HttpServletResponse response) {

		List<Invoice> invoices = InvoiceDB.selectInvoices();
		Collections.reverse(invoices);
		request.setAttribute("invoices", invoices);
		return "/admin_panel/invoices.jsp";
	}

	private String displayLogs(HttpServletRequest request, HttpServletResponse response) {

		List<Download> downloads = DownloadDB.selectDownloads();
		Collections.reverse(downloads);
		request.setAttribute("downloads", downloads);
		return "/admin_panel/downloads.jsp";
	}

	private String displayInvoice(HttpServletRequest request, HttpServletResponse response) {

		// retrieve invoice number
		String requestURI = request.getRequestURI();
		String[] tokens = requestURI.split("/");
		Long invoiceNumber = Long.parseLong(tokens[tokens.length - 2]);

		// get invoice based on its number
		Invoice invoice = InvoiceDB.select(invoiceNumber);
		request.setAttribute("invoice", invoice);

		return "/admin_panel/single_invoice.jsp";
	}

	private String addProduct(HttpServletRequest request, HttpServletResponse response) {

		return "/admin_panel/product_add.jsp";
	}

	private String processInvoice(HttpServletRequest request, HttpServletResponse response) {

		// retrieve an appropriate invoice and make it processed
		Long invoiceNumber = Long.parseLong(request.getParameter("invoiceNumber"));
		Invoice invoice = InvoiceDB.select(invoiceNumber);
		invoice.setProcessed(true);
		InvoiceDB.update(invoice);

		return "/admin/processInvoices";
	}

}
