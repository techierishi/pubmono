package com.rishi.onlinestore.service;

import com.rishi.onlinestore.hibernate.util.HibernateUtil;
import com.rishi.onlinestore.model.Product;
import com.rishi.onlinestore.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ProductService {

   
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
    
    public Product getProductDetail(String pid) {
        Product product = new Product();
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query query = session.createQuery("from Product where product_id="+pid);
            product = (Product) query.uniqueResult();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return product;
    }
}
