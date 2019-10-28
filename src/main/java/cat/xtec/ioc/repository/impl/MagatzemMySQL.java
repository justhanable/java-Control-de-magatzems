/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.xtec.ioc.repository.impl;

import cat.xtec.ioc.domain.Magatzem;
import cat.xtec.ioc.repository.MagatzemRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;


//@Repository
public class MagatzemMySQL implements MagatzemRepository {

    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public MagatzemMySQL(){
         entityManager  = Persistence.createEntityManagerFactory("MagatzemsPersistenceUnit").createEntityManager();
         entityTransaction = entityManager.getTransaction(); 
    }
    
    @Override
    public Magatzem getMagatzemByCodi(String codi) {
        return (Magatzem) entityManager.createQuery("select object(o) from Magatzem o " +
                "where o.codi = :codi")
                .setParameter("codi", codi)
                .getSingleResult();
    }

    @Override
    public List<Magatzem> getAllMagatzems() {
        System.out.println("ENTITI ENTITI ENTITI");
        return (List<Magatzem>) entityManager.createQuery("select object(o) from Magatzem o ")
                  .getResultList();        
    }

    @Override
    public List<Magatzem> getMagatzemsByLocalitat(String localitat) {
        return (List<Magatzem>) entityManager.createQuery("select object(o) from Magatzem o " +
                "where o.localitat = :localitat")
                .setParameter("localitat", localitat)
                .getResultList(); 
    }

    @Override
    public void addMagatzem(Magatzem magatzem) {
        entityTransaction.begin();
        entityManager.persist(magatzem);
        entityTransaction.commit();        
    }

    @Override
    public void updateMagatzem(Magatzem magatzem) {
        entityTransaction.begin();
        entityManager.merge(magatzem);
        entityTransaction.commit();          
    }

}
