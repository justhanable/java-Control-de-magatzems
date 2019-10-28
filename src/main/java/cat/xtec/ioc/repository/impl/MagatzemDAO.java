package cat.xtec.ioc.repository.impl;

import cat.xtec.ioc.domain.Magatzem;
import cat.xtec.ioc.repository.MagatzemRepository;
import cat.xtec.ioc.service.impl.MagatzemServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.stereotype.Repository;

@Repository
public class MagatzemDAO implements MagatzemRepository {

    private Dbconnection dBConnection;
    private Connection connection;

    public MagatzemDAO(Dbconnection dBConnection) {
        this.dBConnection = dBConnection;
    }

    public MagatzemDAO() {
        try {
            dBConnection = (Dbconnection) new InitialContext().lookup("java:global/dawm07eac4magatzem/Dbconnection");
            dBConnection.setConnectionFile("db.properties");
        } catch (NamingException ex) {
            Logger.getLogger(MagatzemServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Magatzem getMagatzemByCodi(String codi) {
        /*
        TODO EAC4
        Objectiu: heu de retornar el magatzem de la BD que coincideix amb el codi del paràmetre
        Restriccions:
        - heu de comprovar que només es retorna un únic resultat
        - heu de gestionar les excepcions que es puguin donar
        - heu de fer servir PreparedStatement (creeu l'objecte de PreparedStatement a partir de
        getPreparedStatement d'aquesta mateixa classe)
        */        
        String qry = "select * from magatzems where codi = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            preparedStatement.setString(1, codi);
            return findUniqueResult(preparedStatement);
        } catch (Exception ex) {
            Logger.getLogger(MagatzemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Magatzem> getAllMagatzems() {
        /*
        TODO EAC4
        Objectiu: heu de retornar tots els magatzems de la BD
        Restriccions:
        - heu de gestionar les excepcions que es puguin donar
        - heu de fer servir PreparedStatement (creeu l'objecte de PreparedStatement a partir de
        getPreparedStatement d'aquesta mateixa classe)
        */        
        String qry = "select * from magatzems";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            List<Magatzem> magatzems = executeQuery(preparedStatement);
            return magatzems;
        } catch (SQLException ex) {
            Logger.getLogger(MagatzemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Magatzem> getMagatzemsByLocalitat(String localitat) {
        /*
        TODO EAC4
        Objectiu: heu de retornar els magatzems de la BD que coincideix amb el localitat del paràmetre
        Restriccions:
        - heu de gestionar les excepcions que es puguin donar
        - heu de fer servir PreparedStatement (creeu l'objecte de PreparedStatement a partir de
        getPreparedStatement d'aquesta mateixa classe)
        */        
        String qry = "select * from magatzems where localitat = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            preparedStatement.setString(1, localitat);
            List<Magatzem> magatzems = executeQuery(preparedStatement);
            return magatzems;
        } catch (SQLException ex) {
            Logger.getLogger(MagatzemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void addMagatzem(Magatzem magatzem) {
        /*
        TODO EAC4
        Objectiu: heu d'afegir el magatzem del paràmetre a la taula magatzems de la BD
        Restriccions:
        - heu de fer servir createOrUpdateMagatzem
        - heu de gestionar les excepcions que es puguin donar
        - heu de fer servir PreparedStatement (creeu l'objecte de PreparedStatement a partir de
        getPreparedStatement d'aquesta mateixa classe)
        */         
        String qry = "INSERT INTO magatzems (codi, denominacio, actiu, localitat) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            preparedStatement.setString(1, magatzem.getCodi());
            preparedStatement.setString(2, magatzem.getDenominacio());
            preparedStatement.setBoolean(3, magatzem.getActiu());
            preparedStatement.setString(4, magatzem.getLocalitat());
            createOrUpdateMagatzem(magatzem.getCodi(), preparedStatement);
        } catch (Exception ex) {
            Logger.getLogger(MagatzemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void updateMagatzem(Magatzem magatzem) {
        /*
        TODO EAC4
        Objectiu: heu d'actualitzar el magatzem del paràmetre a la taula magatzems de la BD
        Restriccions:
        - primer heu d'eliminar el magatzem de la BD, fent servir createOrUpdateMagatzem per a la eliminació
        - desprès feu servir addMagatzem amb el magatzem del paràmetre
        - heu de gestionar les excepcions que es puguin donar
        - heu de fer servir PreparedStatement (creeu l'objecte de PreparedStatement a partir de
        getPreparedStatement d'aquesta mateixa classe)
        */         
        String qry = "DELETE FROM magatzems WHERE codi = ?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = getPreparedStatement(qry);
            preparedStatement.setString(1, magatzem.getCodi());
            createOrUpdateMagatzem(magatzem.getCodi(), preparedStatement);
            this.addMagatzem(magatzem);
        } catch (Exception ex) {
            Logger.getLogger(MagatzemDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Magatzem createOrUpdateMagatzem(String codi, PreparedStatement preparedStatement) throws Exception {
        /*
        TODO EAC4
        Objectiu: heu de retornar el magatzem de la BD amb el codi del paràmetre però un cop
        s'ha exencutat la sentència que conté preparedStatement
        Estratègia:
        - feu servir primer executeUpdateQuery per exencutar el preparedStatement
        - feu servir getMagatzemByCodi per retornar el magatzem
        */         
        int result = executeUpdateQuery(preparedStatement);
        return getMagatzemByCodi(codi);
    }

    private Magatzem findUniqueResult(PreparedStatement preparedStatement) throws Exception {
        /*
        TODO EAC4
        Objectiu: heu de retornar el magatzem que retorna l'execució de la 
        sentència de preparedStatement, però si el resultat és més d'un magatzem
        llavors heu de llançar una excepció.
        Estratègia:
        - executar la sentència de preparedStatement
        - procediu segons el nombre d'elements del resultat
        Restriccions:        
        - heu de gestionar les excepcions que es puguin donar
        - feu servir executeQuery (es refereix al mètode d'aquesta classe)
        */        
        List<Magatzem> magatzems = executeQuery(preparedStatement);
        if (magatzems.isEmpty()) {
            return null;
        }
        if (magatzems.size() > 1) {
            throw new Exception("Only one result expected");
        }
        return magatzems.get(0);
    }

    private List<Magatzem> executeQuery(PreparedStatement preparedStatement) {
        /*
        TODO EAC4
        Objectiu: heu de retornar la llista de magatzems que retorna
        l'execució de la sentència de preparedStatement
        Estratègia:
        - com l'execució de la sentència de preparedStatement retorna un
        ResultSet, heu de recórrer aquest ResultSet i anant convertint
        els elements del ResulSet en magatzems -> feu servir buildMagatzemFromResultSet
        que fa la conversió
        Restriccions:        
        - heu de gestionar les excepcions que es puguin donar
        - feu servir executeQuery (és refereix al mètode de preparedStatement)
        */        
        List<Magatzem> magatzems = new ArrayList<>();

        try (
                ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Magatzem magatzem = buildMagatzemFromResultSet(rs);
                magatzems.add(magatzem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return magatzems;
    }

    private PreparedStatement getPreparedStatement(String query) throws SQLException {
        if (getConnection() == null) {
            try {
                setConnection(dBConnection.getConnection());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        return getConnection().prepareStatement(query);
    }

    private int executeUpdateQuery(PreparedStatement preparedStatement) {
        /*
        TODO EAC4
        Objectiu: heu de retornar el nombre sencer que retorna
        l'execució de la sentència de preparedStatement
        Nota:
        - en aquest cas la sentència de preparedStatement és
        d'actualització (INSERT, UPDATE, DELETE)        
        Restriccions:        
        - heu de gestionar les excepcions que es puguin donar
        - feu servir executeUpdate (és refereix al mètode de preparedStatement)
        */        
        int result = 0;
        if (getConnection() == null) {
            try {
                setConnection(dBConnection.getConnection());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
        try {
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Magatzem buildMagatzemFromResultSet(ResultSet rs) throws SQLException {
        /*
        TODO EAC4
        Objectiu: retorna un nou objecte de Magatzem a partir del de ResultSet        
        */           
        String codi = rs.getString("codi");
        String denominacio = rs.getString("denominacio");
        Boolean actiu = rs.getBoolean("actiu");
        String localitat = rs.getString("localitat");
        Magatzem magatzem = new Magatzem(codi, denominacio, actiu, localitat);
        return magatzem;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
