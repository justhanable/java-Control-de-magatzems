/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cat.xtec.ioc.service.impl;

import cat.xtec.ioc.domain.Magatzem;
import cat.xtec.ioc.repository.MagatzemRepository;
import cat.xtec.ioc.repository.impl.Dbconnection;
import cat.xtec.ioc.service.MagatzemService;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author professor
 */
@Service
public class MagatzemServiceImpl implements MagatzemService{
    @Autowired
    private MagatzemRepository magatzemRepository;
    
    


    @Override
    public Magatzem getMagatzemByCodi(String codi) {        
        return magatzemRepository.getMagatzemByCodi(codi);
    }

    @Override
    public List<Magatzem> getAllMagatzems() {
        return magatzemRepository.getAllMagatzems();
    }

    @Override
    public List<Magatzem> getMagatzemsByLocalitat(String localitat) {
        return magatzemRepository.getMagatzemsByLocalitat(localitat);
    }
    

    @Override
    public void addMagatzem(Magatzem magatzem) {
        magatzemRepository.addMagatzem(magatzem);
    }
    
    @Override
    public void updateMagatzem(Magatzem magatzem) {
        magatzemRepository.updateMagatzem(magatzem);
    }
    
}
