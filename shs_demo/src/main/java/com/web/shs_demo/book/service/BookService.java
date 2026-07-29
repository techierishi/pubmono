package com.web.shs_demo.book.service;

import java.util.List;

import com.web.shs_demo.account.model.User;
import com.web.shs_demo.book.dao.BookDao;
import com.web.shs_demo.book.model.Book;

public class BookService {
    private BookDao bookDao;

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getBookList() {
        return bookDao.getBookList();
    }
    public List<Book> getBookListByUser(User user,int firstResult) {
        return bookDao.getBookListByUser(user,firstResult);
    }
    public long getBookListCountByUser(User user) {
        return bookDao.getBookListCountByUser(user);

    }
    public Book getBook(Long bookId) {
        return bookDao.getBook(bookId);
    }

    public void saveOrUpdateBook(Book book) {
        bookDao.saveOrUpdateBook(book);
    }

    public void deleteBook(Long bookId) {
        bookDao.deleteBook(bookId);
    }
}
