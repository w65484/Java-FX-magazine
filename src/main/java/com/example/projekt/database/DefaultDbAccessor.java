package com.example.projekt.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;

/**
 * Class for accessing the app's database.
 * @param <T> Type of entity which we want to work with
 */
public record DefaultDbAccessor<T>(Class<T> type) {

    /**
     * Static session factory for the database.
     */
    private static SessionFactory sessionFactory;

    /**
     * Static method for creating the singleton session factory.
     */
    private static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static boolean checkConnection(){
        try {
            SessionFactory sessionFactory = getSessionFactory();
            Session session = sessionFactory.openSession();
            session.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Method for getting all records of Type <b>T</b> from database as ArrayList.
     * @param name search entity based on name
     * @return List of all records of Type <b>T</b>
     */
    public ArrayList<T> getAll(String name) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);

        if(name != null && !name.isEmpty()){
            all.where(cb.like(rootEntry.get("name"), "%" + name + "%"));
        }

        TypedQuery<T> allQuery = session.createQuery(all);
        return new ArrayList<>(allQuery.getResultList());
    }

    /**
     * Method for getting all records of Type <b>T</b> from database as ArrayList.
     * @param name search entity based on name
     * @param limit limit of records
     * @return List of all records of Type <b>T</b>
     */
    public ArrayList<T> getAll(String name, int limit) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> rootEntry = cq.from(type);
        CriteriaQuery<T> all = cq.select(rootEntry);

        if(name != null && !name.isEmpty()){
            all.where(cb.like(rootEntry.get("name"), "%" + name + "%"));
        }
        TypedQuery<T> allQuery = session.createQuery(all);
        allQuery.setMaxResults(limit);
        return new ArrayList<>(allQuery.getResultList());
    }

    /**
     * Method for deleting record of Type <b>T</b> from database.
     * @param entity Entity which we want to delete
     * @return True if the record was deleted or false in case of an error
     */
    public boolean delete(T entity) {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            session.close();
        } catch (PersistenceException e) {
            return false;
        }

        return true;
    }

    /**
     * Method for saving record of Type <b>T</b> into database.
     * @param editingItem Entity which we want to save
     * @param editMode True if we want to edit existing record or false if we want to create new one
     */
    public void save(T editingItem, boolean editMode) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(editMode) {
            session.update(editingItem);
        }
        else {
            session.save(editingItem);
        }
        session.getTransaction().commit();
        session.close();
    }
}
