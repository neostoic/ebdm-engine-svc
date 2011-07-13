/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAccount;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Repository("dmAccountDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class DmAccountDaoImpl implements DmAccountDao {

    private SessionFactory sessionFactory;

    @Autowired(required = true)
    public void setSessionFactory(@Qualifier(value="dm_engine") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }

    public DmAccount save(DmAccount account) {
        if(account.getId() == null)
            account.setId(java.util.UUID.randomUUID().toString());

        sessionFactory.getCurrentSession().save(account);
        flush();
        
        return account;
    }

    public DmAccount getByName(String name) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DmAccount.class);
        crit.add(Restrictions.eq("name", name).ignoreCase());
        crit.add(Restrictions.eq("isRemoved", false));
        return (DmAccount) crit.uniqueResult();
    }

    public List<DmAccount> getList(String searchTerm, int recordStartNo, int pageNumItems) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DmAccount.class);
        crit.add(Restrictions.like("name", "%" + searchTerm + "%").ignoreCase());
        crit.add(Restrictions.eq("isRemoved", false));
        crit.addOrder(Order.asc("name"));
        crit.setFirstResult(recordStartNo);
        crit.setMaxResults(pageNumItems);
        return crit.list();
    }

    public int getListNum(String searchTerm) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DmAccount.class);
        crit.add(Restrictions.like("name", "%" + searchTerm + "%").ignoreCase());
        crit.add(Restrictions.eq("isRemoved", false));
        return crit.list().size();
    }

    public DmAccount get(String id) {
        return (DmAccount) sessionFactory.getCurrentSession().get(DmAccount.class, id);
    }

    public DmAccount getActive(String id) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DmAccount.class);
        crit.add(Restrictions.eq("id", id));
        crit.add(Restrictions.eq("isActive", true));
        crit.add(Restrictions.eq("isRemoved", false));
        return (DmAccount) crit.uniqueResult();
    }

    public boolean delete(DmAccount account) {
        try {
            sessionFactory.getCurrentSession().delete(account);
        }
        catch (HibernateException ex) {
            return false;
        }
        return true;
    }

    public DmAccount getByNameExcludeById(String name, String id) {
        Criteria crit = sessionFactory.getCurrentSession().createCriteria(DmAccount.class);
        crit.add(Restrictions.eq("name", name).ignoreCase());
        crit.add(Restrictions.ne("id", id));
        crit.add(Restrictions.eq("isRemoved", false));
        return (DmAccount) crit.uniqueResult();
    }

    public boolean update(DmAccount account) {
        try {
            sessionFactory.getCurrentSession().update(account);
        }
        catch (HibernateException ex) {
            return false;
        }
        return true;
    }
}
