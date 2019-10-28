package cat.xtec.ioc.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "magatzems")
public class Magatzem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "codi")
    @NotNull
    @Size(max = 100)
    private String codi;

    @Column(name = "denominacio")
    @NotNull
    @Size(max = 100)
    private String denominacio;

    @Column(name = "actiu")
    @NotNull
    private Boolean actiu;

    @Column(name = "localitat")
    @NotNull
    @Size(max = 50)
    private String localitat;
    

    public Magatzem(String codi, String denominacio, Boolean actiu, String localitat) {
        this.codi = codi;
        this.denominacio = denominacio;
        this.actiu = actiu;
        this.localitat = localitat;
    }

    public Magatzem() {
    }

    public Magatzem(String localitat) {
        this.localitat = localitat;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getDenominacio() {
        return denominacio;
    }

    public void setDenominacio(String denominacio) {
        this.denominacio = denominacio;
    }

    public Boolean getActiu() {
        return actiu;
    }

    public void setActiu(Boolean actiu) {
        this.actiu = actiu;
    }

    public String getLocalitat() {
        return localitat;
    }

    public void setLocalitat(String localitat) {
        this.localitat = localitat;
    }

}
