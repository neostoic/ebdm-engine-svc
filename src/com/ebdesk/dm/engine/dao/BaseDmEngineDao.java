/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmDocumentVersion;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author designer
 */
public interface BaseDmEngineDao<T> {

    public void setSessionFactory(SessionFactory sessionFactory);

    public Session getSession();

    public Serializable save(T t);

    public T findById(Serializable id);

    
    /***
     * Find objects with ilike expression.
     * @param key the object's atrribute name.
     * @param object value to match.
     * @param start start row to fetch
     * @param max maximum row to fetch.
     * @return List of object.
     */
    public List<T> findByKey(String key, Object object, int start, int max);

    public List<T> findAll();
    
    public List<T> findAll(int start, int max);
    
    
    /****
     * Find object by id. If the object is exist return true, else false.
     * @param id the object identifier. object id must be defined as id field.
     * @return if the object is exist return true, else false.
     */
    public boolean isExist(Serializable id);
    
    public void update(T t);
    
    public void delete(T t);
    
    /***
     * Count all of object in table.
     */
    public Integer countAll();

    //public void flush();
}
