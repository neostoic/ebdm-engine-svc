/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("baseDmEngineDao")
public class BaseDmEngineDaoImpl<T> implements BaseDmEngineDao<T> {

    protected T t;

    private SessionFactory sessionFactory;

    public BaseDmEngineDaoImpl() {
        
    }    

    @Autowired(required = true)
    public void setSessionFactory(@Qualifier(value="dm_engine") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        if (this.sessionFactory == null) {
            throw new RuntimeException("sessionFactory is not initialized.");
        }

        return this.sessionFactory.getCurrentSession();
    }
    
    public Serializable save(T t){
        return getSession().save(t);
    }

    public T findById(Serializable id){
        return (T) getSession().get(t.getClass(), id);
    }

    
    public List<T> findByKey(String key, Object object, int start, int max){
        Criteria criteria = getSession().createCriteria(t.getClass());
        criteria.add(Restrictions.ilike(key, object));        
        criteria.setFirstResult(start);
        criteria.setMaxResults(max);
        List result = criteria.list();        
        return result;
    }
    
    public List<T> findAll(){
        Criteria criteria = getSession().createCriteria(t.getClass());
        List result = criteria.list();        
        return result;
    }
    
    public List<T> findAll(int start, int max){
        Criteria criteria = getSession().createCriteria(t.getClass());
        criteria.setFirstResult(start);
        criteria.setMaxResults(max);       
        List result = criteria.list();        
        return result;
    }

    public boolean isExist(Serializable id){
        Criteria criteria = getSession().createCriteria(t.getClass());
        criteria.add(Restrictions.eq("id", id));
        criteria.setProjection(Projections.rowCount());
        List result = criteria.list();
        Integer rowCount = new Integer(0);
        if (result.size() > 0) {
            rowCount = (Integer) result.get(0);
            if (rowCount > 0) {
                return true;
            }
        }
        return false;
    }

    public void update(T t){
        getSession().update(t);
    }
    
    public void delete(T t){
        getSession().delete(t);        
    }
    
    public Integer countAll(){
        Criteria criteria = getSession().createCriteria(t.getClass());
        criteria.setProjection(Projections.rowCount());
               
        List<Integer> list = criteria.list();
        if (list.size() > 0) {
            return list.get(0);
        }        
        return 0;
    }

}
