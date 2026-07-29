package com.web.shs_demo.book.action;

import java.util.ArrayList;
import java.util.List;

import com.web.shs_demo.account.model.User;
import com.web.shs_demo.book.model.Book;
import com.web.shs_demo.book.service.BookService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BookAction extends ActionSupport {
	private static final long serialVersionUID = 2538923417705852774L;

	private Long bookId;
	private Book book;
	private List<Book> bookList;
	private long pages = 0;
	private int page = 0;

	private BookService bookService;

	public String list() throws Exception {
		if (bookList == null) {
			bookList = new ArrayList<Book>();
		}

		int pageSize = 4;
		System.out.println(" pageSize : " + pageSize);
		if (page != 0) {
			page = page * pageSize;
		}
		
		bookList.addAll(bookService.getBookListByUser(getCurrentUser(), page));
		if (null != bookList && !bookList.isEmpty()) {
			long count = bookService.getBookListCountByUser(getCurrentUser());
			System.out.println(" count : " + count);
			pages = count / pageSize;
			System.out.println(" pages 1 : " + pages);

			if ((pages * pageSize) > count) {
				pages += 1;
			}
			System.out.println(" pages 2 : " + pages);

		}
		return "list";
	}

	public String show() throws Exception {
		book = getCurrentUser().getBook(bookId);
		return "show";
	}

	public String input() throws Exception {
		if (bookId != null) {
			book = getCurrentUser().getBook(bookId);
		}
		return INPUT;
	}

	public String saveOrUpdate() throws Exception {
		book.setUser(getCurrentUser());
		bookService.saveOrUpdateBook(book);
		return SUCCESS;
	}

	public String delete() throws Exception {
		Book book = getCurrentUser().getBook(bookId);
		if (book != null) {
			bookService.deleteBook(bookId);
			getCurrentUser().getBooks().remove(book);
		}
		return SUCCESS;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setBookService(BookService bookService) {
		this.bookService = bookService;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	private User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get(User.SESSION_KEY);
	}
}
