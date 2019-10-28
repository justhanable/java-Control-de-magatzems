package cat.xtec.ioc.repository;

import cat.xtec.ioc.domain.Magatzem;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author professor
 */
public interface MagatzemRepository {
    
    Magatzem getMagatzemByCodi(String codi);
    
    List<Magatzem> getAllMagatzems();  

    List<Magatzem> getMagatzemsByLocalitat(String localitat);
    
    void addMagatzem(Magatzem magatzem);
    
    void updateMagatzem(Magatzem magatzem);
    
}
