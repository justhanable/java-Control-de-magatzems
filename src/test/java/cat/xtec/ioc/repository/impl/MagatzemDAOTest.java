package cat.xtec.ioc.repository.impl;

import cat.xtec.ioc.domain.Magatzem;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MagatzemDAOTest {

    private Dbconnection dBConnection;
    private String connectionProperties = "db_test.properties";
    MagatzemDAO magatzemDAO;

    @Before
    public void setUp() {
        dBConnection = new Dbconnection(connectionProperties);
        magatzemDAO = new MagatzemDAO(dBConnection);
    }

    @After
    public void tearDown() throws IOException, SQLException {
        magatzemDAO.getConnection().close();
    }

    

    
    
    
    @Test
    public void getMagatzemByCodi() throws Exception {
        String existingCodi = "010";
        String unknownCodi = "256";

        Magatzem magatzem = magatzemDAO.getMagatzemByCodi(existingCodi);
        Assert.assertNotNull(magatzem);
        magatzem = magatzemDAO.getMagatzemByCodi(unknownCodi);
        Assert.assertNull(magatzem);
    }
    
    @Test
    public void getAllMagatzems() throws SQLException {
        List<Magatzem> magatzems = magatzemDAO.getAllMagatzems();
        Assert.assertEquals("Hauriem de tenir 6 magatzems a la base de dades", 6, magatzems.size());
    }
    
    @Test
    public void getMagatzemByLocalitat() throws Exception {
        String existingLocalitat = "Badalona";
        String unknownLocalitat = "Granollers";
        List<Magatzem> magatzems = magatzemDAO.getMagatzemsByLocalitat(existingLocalitat);
        Assert.assertNotEquals(0, magatzems.size());
        magatzems = magatzemDAO.getMagatzemsByLocalitat(unknownLocalitat);
        Assert.assertEquals(0, magatzems.size());
    }

    @Test
    public void addMagatzem() throws Exception {
        String codi = "550";
        String denominacio = "Bodega de melons";
        Boolean actiu = true;
        String localitat = "Badalona";
        Magatzem magatzem = new Magatzem(codi, denominacio, actiu, localitat);
        magatzemDAO.addMagatzem(magatzem);
        Magatzem createdMagatzem = magatzemDAO.getMagatzemByCodi(codi);
        Assert.assertNotNull(createdMagatzem);        
    }
    
    
    

    
    @Test
    public void updateMagatzem() throws Exception {
        String novaDenominacio = "Bodega de maduixes";
        Magatzem magatzem = magatzemDAO.getMagatzemByCodi("020");
        Assert.assertNotNull(magatzem);
        magatzem.setDenominacio(novaDenominacio);
        magatzemDAO.updateMagatzem(magatzem);
        Magatzem updatedMagatzem = magatzemDAO.getMagatzemByCodi(magatzem.getCodi());
        Assert.assertEquals(novaDenominacio, updatedMagatzem.getDenominacio());        
    }

    


}
