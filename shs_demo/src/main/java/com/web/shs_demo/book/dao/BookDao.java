package com.web.shs_demo.book.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.web.shs_demo.account.model.User;
import com.web.shs_demo.book.model.Book;

public class BookDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Book> getBookList() {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from Book b");
            List<Book> bookList = query.list();
            return bookList;
        } finally {
            session.close();
        }
    }
    
    public List<Book> getBookListByUser(User user,int firstResult) {
        Session session = sessionFactory.openSession();
        try {
            Criteria query = session.createCriteria(Book.class).add(Restrictions.eq("user.id", user.getId()));
            query.setFirstResult(firstResult);
            query.setMaxResults(4);
            List<Book> bookList = query.list();
            return bookList;
        } finally {
            session.close();
        }
    }
    
    public long getBookListCountByUser(User user) {
        Session session = sessionFactory.openSession();
        try {
            Criteria query = session.createCriteria(Book.class).setProjection(Projections.rowCount());
           
            Long bookCount = (Long) query.uniqueResult();
            return bookCount;
        } finally {
            session.close();
        }
    }

    public Book getBook(Long bookId) {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from Book b where b.id = :id");
            query.setLong("id", bookId);
            query.setMaxResults(1);
            return (Book)query.uniqueResult();
        } finally {
            session.close();
        }
    }

    public void saveOrUpdateBook(Book book) {
        Session session = sessionFactory.openSession();
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            session.saveOrUpdate(book);
            ts.commit();
        } finally {
            session.close();
        }
    }

    public void deleteBook(Long bookId) {
        Session session = sessionFactory.openSession();
        Transaction ts = null;
        try {
            ts = session.beginTransaction();
            Book book = (Book)session.get(Book.class, bookId);
            session.delete(book);
            ts.commit();
        } finally {
            session.close();
        }
    }
}
