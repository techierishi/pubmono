package com.pavitra.data;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * This class can be used to get an instance of the EntityManagerFactory class,
 * which enables retrieving data using JPA. 
 * 
 * @author Rishikesh Jha
 */
public class DBUtil {
   
   private static final EntityManagerFactory emf = 
           Persistence.createEntityManagerFactory("MusicStorePU");
   
   public static EntityManagerFactory getEmFactory() {
      return emf;
   }
}
