package com.pavitra.business;

import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"content"})
public class FileMeta {
	private String fileName;
	private String filePath;
    private String fileSize;
    private String fileType; 
    private InputStream content;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public InputStream getContent() {
		return content;
	}

	public void setContent(InputStream content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "FileMeta [fileName=" + fileName + ", filePath=" + filePath + ", fileSize=" + fileSize + ", fileType="
				+ fileType + "]";
	}
    
}
