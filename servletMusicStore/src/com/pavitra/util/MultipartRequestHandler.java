package com.pavitra.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.pavitra.business.FileMeta;

public class MultipartRequestHandler {
	public static List<FileMeta> uploadByJavaServletAPI(HttpServletRequest request)
			throws IOException, ServletException {

		List<FileMeta> files = new LinkedList<FileMeta>();

		// 1. Get all parts
		Collection<Part> parts = request.getParts();
		

		// 3. Go over each part
		FileMeta temp = null;
		for (Part part : parts) {
			System.out.println(" __ "+part.getName());

			// 3.1 if part is multiparts "file"
			if (part.getContentType() != null) {

				// 3.2 Create a new FileMeta object
	            String fileName = StringUtil.getRandomStr()+getFilename(part);

				temp = new FileMeta();
				temp.setFileName(fileName);
				temp.setFilePath(request.getContextPath()+Const.UPLOADS);
				temp.setFileSize(part.getSize() / 1024 + " Kb");
				temp.setFileType(part.getContentType());
				temp.setContent(part.getInputStream());
					            
				// 3.3 Add created FileMeta object to List<FileMeta> files
				files.add(temp);

			}
		}
		return files;
	}

	public static void uploadFileNow(HttpServletRequest request, String fileName, InputStream is)
			throws FileNotFoundException, IOException {
		String outputfile = request.getServletContext().getRealPath(Const.UPLOADS+fileName);  // get path on the server
		FileOutputStream os = new FileOutputStream (outputfile);
		
		// write bytes taken from uploaded file to target file
		int ch = is.read();
		while (ch != -1) {
		     os.write(ch);
		     ch = is.read();
		}
		os.close();
	}

	// this method is used to get file name out of request headers
	//
	private static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE
																													// fix.
			}
		}
		return null;
	}

}
