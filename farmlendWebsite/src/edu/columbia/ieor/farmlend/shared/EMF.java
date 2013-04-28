package edu.columbia.ieor.farmlend.shared;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EMF {
    private static final EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("transactions-optional");

        private EMF() {}
        
        public static EntityManager get() {
            return emfInstance.createEntityManager();
        }

        public static void save(EntityManager entityManager, Object o) {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        }
        
        @SuppressWarnings("unchecked")
        public static <T> Collection<T> loadAll(EntityManager entityManager, String tableName, Class<T> k) {
            Query query = entityManager.createQuery("SELECT e FROM " + tableName + " e", k);
            Collection<T> returnValue = new ArrayList<T>(query.getResultList());
            return returnValue;
        }
        
        public static <T> T findById(EntityManager entityManager, Class<T> k, Long id) {
            T result = entityManager.find(k, id);
            return result;
        }
}
