package com.github.webtictactoe.tictactoe.utils;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * A container for entities, base class for PlayerRegistry.
 * The fundamental common operations are here (CRUD).
 *
 * T is type for items in container K is type of id (primary key)
 *
 * @author hajo
 */
public abstract class AbstractDAO<T, K> implements IDAO<T, K> {

    private EntityManagerFactory entityManagerFactory;
    private final Class<T> clazz;
    
    protected AbstractDAO(Class<T> clazz, String puName) {
        this.clazz = clazz;
        entityManagerFactory = Persistence.createEntityManagerFactory(puName);
    }
    
    @Override
    public void add(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        entityTransaction.begin();
        entityManager.persist(t);
        entityTransaction.commit();
        
        entityManager.close();
    }

    @Override
    public void remove(K name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        entityTransaction.begin();
        entityManager.remove(entityManager.getReference(clazz, name));
        entityTransaction.commit();
        
        entityManager.close();
    }

    @Override
    public void update(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        entityTransaction.begin();
        entityManager.merge(t);
        entityTransaction.commit();
        
        entityManager.close();
    }

    @Override
    public T find(K name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        
        entityTransaction.begin();
        T foundObject = entityManager.find(clazz, name);
        entityTransaction.commit();
        
        entityManager.close();
        
        return foundObject;
    }

    @Override
    public List<T> getRange(int first, int nItems) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<T> result = entityManager.createQuery("SELECT p FROM " + clazz.getSimpleName() + " p").getResultList();
        return result.subList(first, nItems);
    }

    @Override
    public int getCount() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return ((Long) entityManager.createQuery("SELECT COUNT(c) FROM " + clazz.getSimpleName() + " c").getSingleResult()).intValue();
    }

}
