/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author yashkhandha
 */
@Entity
@Table(name = "RESIDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resident.findAll", query = "SELECT r FROM Resident r")
    , @NamedQuery(name = "Resident.findByResid", query = "SELECT r FROM Resident r WHERE r.resid = :resid")
    , @NamedQuery(name = "Resident.findByFirstname", query = "SELECT r FROM Resident r WHERE UPPER(r.firstname) = UPPER(:firstname)")
    , @NamedQuery(name = "Resident.findBySurname", query = "SELECT r FROM Resident r WHERE UPPER(r.surname) = UPPER(:surname)")
    , @NamedQuery(name = "Resident.findByDateofbirth", query = "SELECT r FROM Resident r WHERE r.dateofbirth = :dateofbirth")
    , @NamedQuery(name = "Resident.findByAddress", query = "SELECT r FROM Resident r WHERE UPPER(r.address) = UPPER(:address)")
    , @NamedQuery(name = "Resident.findByPostcode", query = "SELECT r FROM Resident r WHERE r.postcode = :postcode")
    , @NamedQuery(name = "Resident.findByEmail", query = "SELECT r FROM Resident r WHERE UPPER(r.email) = UPPER(:email)")
    , @NamedQuery(name = "Resident.findByMobilenumber", query = "SELECT r FROM Resident r WHERE r.mobilenumber = :mobilenumber")
    , @NamedQuery(name = "Resident.findByNumberofresidents", query = "SELECT r FROM Resident r WHERE r.numberofresidents = :numberofresidents")
    , @NamedQuery(name = "Resident.findByEnergyprovider", query = "SELECT r FROM Resident r WHERE UPPER(r.energyprovider) = UPPER(:energyprovider)")})
public class Resident implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESID")
    private Integer resid;
    @Size(max = 20)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 20)
    @Column(name = "SURNAME")
    private String surname;
    @Column(name = "DATEOFBIRTH")
    @Temporal(TemporalType.DATE)
    
    private Date dateofbirth;
    @Size(max = 100)
    @Column(name = "ADDRESS")
    private String address;
    @Size(max = 20)
    @Column(name = "POSTCODE")
    private String postcode;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 20)
    @Column(name = "MOBILENUMBER")
    private String mobilenumber;
    @Column(name = "NUMBEROFRESIDENTS")
    private Integer numberofresidents;
    @Size(max = 30)
    @Column(name = "ENERGYPROVIDER")
    private String energyprovider;
    @OneToMany(mappedBy = "resid")
    private Collection<ResidentCredential> residentCredentialCollection;
    @OneToMany(mappedBy = "resid")
    private Collection<ElectricityUsage> electricityUsageCollection;

    public Resident() {
    }

    public Resident(Integer resid) {
        this.resid = resid;
    }

    public Integer getResid() {
        return resid;
    }

    public void setResid(Integer resid) {
        this.resid = resid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public Integer getNumberofresidents() {
        return numberofresidents;
    }

    public void setNumberofresidents(Integer numberofresidents) {
        this.numberofresidents = numberofresidents;
    }

    public String getEnergyprovider() {
        return energyprovider;
    }

    public void setEnergyprovider(String energyprovider) {
        this.energyprovider = energyprovider;
    }

    @XmlTransient
    public Collection<ResidentCredential> getResidentCredentialCollection() {
        return residentCredentialCollection;
    }

    public void setResidentCredentialCollection(Collection<ResidentCredential> residentCredentialCollection) {
        this.residentCredentialCollection = residentCredentialCollection;
    }

    @XmlTransient
    public Collection<ElectricityUsage> getElectricityUsageCollection() {
        return electricityUsageCollection;
    }

    public void setElectricityUsageCollection(Collection<ElectricityUsage> electricityUsageCollection) {
        this.electricityUsageCollection = electricityUsageCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resid != null ? resid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resident)) {
            return false;
        }
        Resident other = (Resident) object;
        if ((this.resid == null && other.resid != null) || (this.resid != null && !this.resid.equals(other.resid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Resident[ resid=" + resid + " ]";
    }
    
}
