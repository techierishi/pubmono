package com.rishi.onlinestore.admin.service;

import com.rishi.onlinestore.hibernate.util.HibernateUtil;
import com.rishi.onlinestore.lib.DBG;
import com.rishi.onlinestore.model.Category;
import com.rishi.onlinestore.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CategoryService {

     public boolean categoryadd(Category category) {
        Session session = HibernateUtil.openSession();
       
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            session.saveOrUpdate(category);
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

    public List<Category> getCategoryList() {
        List<Category> list = new ArrayList<Category>();
        Session session = HibernateUtil.openSession();
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            list = session.createQuery("from Category").list();
            tx.commit();
            DBG.d("getCategoryList", ""+list);

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
