package com.rishi.onlinestore.admin.service;

import com.rishi.onlinestore.service.*;
import com.rishi.onlinestore.hibernate.util.HibernateUtil;
import com.rishi.onlinestore.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LoginService {

    public boolean authenticateUser(String email, String password) {
        User user = getUserByUserId(email);
        if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUserByUserId(String email) {
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        User user = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("from User where email='" + email + "'");
            user = (User) query.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public List<User> getListOfUsers() {
        List<User> list = new ArrayList<User>();
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            list = session.createQuery("from User").list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return list;
    }
}
