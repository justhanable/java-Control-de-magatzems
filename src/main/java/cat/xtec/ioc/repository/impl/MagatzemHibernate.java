/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.xtec.ioc.repository.impl;

import org.hibernate.Criteria;
import cat.xtec.ioc.domain.Magatzem;
import cat.xtec.ioc.repository.MagatzemRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Transactional
//@Repository
public class MagatzemHibernate implements MagatzemRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Magatzem getMagatzemByCodi(String codi) {
        return getSession().get(Magatzem.class, codi);
    }

    @Override
    public List<Magatzem> getAllMagatzems() {
        Criteria criteria = createEntityCriteria();
        return (List<Magatzem>) criteria.list();
    }

    @Override
    public List<Magatzem> getMagatzemsByLocalitat(String localitat) {
        return (List<Magatzem>) getSession().get(Magatzem.class, localitat);
    }

    @Override
    public void addMagatzem(Magatzem magatzem) {        
        getSession().saveOrUpdate(magatzem);
    }

    @Override
    public void updateMagatzem(Magatzem magatzem) {
        getSession().merge(magatzem);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private Criteria createEntityCriteria() {
        return getSession().createCriteria(Magatzem.class);
    }

}
