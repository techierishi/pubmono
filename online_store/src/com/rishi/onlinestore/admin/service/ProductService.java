package com.rishi.onlinestore.admin.service;

import com.rishi.onlinestore.hibernate.util.HibernateUtil;
import com.rishi.onlinestore.model.Product;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductService {

   public boolean productAdd(Product product) {
        Session session = HibernateUtil.openSession();
       
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(product);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return true;
    }
    public List<Product> getListOfProducts() {
        List<Product> list = new ArrayList<Product>();
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            list = session.createQuery("from Product").list();
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
