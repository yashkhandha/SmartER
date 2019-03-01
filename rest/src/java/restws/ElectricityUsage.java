/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yashkhandha
 */
@Entity
@Table(name = "ELECTRICITY_USAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ElectricityUsage.findAll", query = "SELECT e FROM ElectricityUsage e")
    , @NamedQuery(name = "ElectricityUsage.findByUsageid", query = "SELECT e FROM ElectricityUsage e WHERE e.usageid = :usageid")
    , @NamedQuery(name = "ElectricityUsage.findByResid", query= "SELECT e from ElectricityUsage e WHERE e.resid.resid = :resid")    
    , @NamedQuery(name = "ElectricityUsage.findByUsagedate", query = "SELECT e FROM ElectricityUsage e WHERE e.usagedate = :usagedate")
    , @NamedQuery(name = "ElectricityUsage.findByUsagehour", query = "SELECT e FROM ElectricityUsage e WHERE e.usagehour = :usagehour")
    , @NamedQuery(name = "ElectricityUsage.findByFridgeusage", query = "SELECT e FROM ElectricityUsage e WHERE e.fridgeusage = :fridgeusage")
    , @NamedQuery(name = "ElectricityUsage.findByAirconditionerusage", query = "SELECT e FROM ElectricityUsage e WHERE e.airconditionerusage = :airconditionerusage")
    , @NamedQuery(name = "ElectricityUsage.findByWashingmachineusage", query = "SELECT e FROM ElectricityUsage e WHERE e.washingmachineusage = :washingmachineusage")
    , @NamedQuery(name = "ElectricityUsage.findByTemperature", query = "SELECT e FROM ElectricityUsage e WHERE e.temperature = :temperature")
    , @NamedQuery(name = "ElectricityUsage.findByEnergyproviderWithUsagehour", query = "SELECT e FROM ElectricityUsage e WHERE e.resid.energyprovider = :energyprovider AND e.usagehour BETWEEN :from AND :to")
})
public class ElectricityUsage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "USAGEID")
    private Integer usageid;
    @Column(name = "USAGEDATE")
    @Temporal(TemporalType.DATE)
    private Date usagedate;
    @Column(name = "USAGEHOUR")
    private Integer usagehour;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "FRIDGEUSAGE")
    private BigDecimal fridgeusage;
    @Column(name = "AIRCONDITIONERUSAGE")
    private BigDecimal airconditionerusage;
    @Column(name = "WASHINGMACHINEUSAGE")
    private BigDecimal washingmachineusage;
    @Column(name = "TEMPERATURE")
    private BigDecimal temperature;
    @JoinColumn(name = "RESID", referencedColumnName = "RESID")
    @ManyToOne
    private Resident resid;

    public ElectricityUsage() {
    }

    public ElectricityUsage(Integer usageid) {
        this.usageid = usageid;
    }

    public Integer getUsageid() {
        return usageid;
    }

    public void setUsageid(Integer usageid) {
        this.usageid = usageid;
    }

    public Date getUsagedate() {
        return usagedate;
    }

    public void setUsagedate(Date usagedate) {
        this.usagedate = usagedate;
    }

    public Integer getUsagehour() {
        return usagehour;
    }

    public void setUsagehour(Integer usagehour) {
        this.usagehour = usagehour;
    }

    public BigDecimal getFridgeusage() {
        return fridgeusage;
    }

    public void setFridgeusage(BigDecimal fridgeusage) {
        this.fridgeusage = fridgeusage;
    }

    public BigDecimal getAirconditionerusage() {
        return airconditionerusage;
    }

    public void setAirconditionerusage(BigDecimal airconditionerusage) {
        this.airconditionerusage = airconditionerusage;
    }

    public BigDecimal getWashingmachineusage() {
        return washingmachineusage;
    }

    public void setWashingmachineusage(BigDecimal washingmachineusage) {
        this.washingmachineusage = washingmachineusage;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usageid != null ? usageid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ElectricityUsage)) {
            return false;
        }
        ElectricityUsage other = (ElectricityUsage) object;
        if ((this.usageid == null && other.usageid != null) || (this.usageid != null && !this.usageid.equals(other.usageid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.ElectricityUsage[ usageid=" + usageid + " ]";
    }
    
}
