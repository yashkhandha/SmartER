/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yashkhandha
 */
@Entity
@Table(name = "RESIDENT_CREDENTIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResidentCredential.findAll", query = "SELECT r FROM ResidentCredential r")
    , @NamedQuery(name = "ResidentCredential.findByUsername", query = "SELECT r FROM ResidentCredential r WHERE UPPER(r.username) = UPPER(:username)")
    , @NamedQuery(name = "ResidentCredential.findByPasswordhash", query = "SELECT r FROM ResidentCredential r WHERE UPPER(r.passwordhash) = UPPER(:passwordhash)")
    , @NamedQuery(name = "ResidentCredential.findByRegistrationdate", query = "SELECT r FROM ResidentCredential r WHERE r.registrationdate = :registrationdate")
    , @NamedQuery(name = "ResidentCredential.findByResid", query= "SELECT r from ResidentCredential r WHERE r.resid.resid = :resid")})
public class ResidentCredential implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERNAME")
    private String username;
    @Size(max = 100)
    @Column(name = "PASSWORDHASH")
    private String passwordhash;
    @Column(name = "REGISTRATIONDATE")
    @Temporal(TemporalType.DATE)
    private Date registrationdate;
    @JoinColumn(name = "RESID", referencedColumnName = "RESID")
    @ManyToOne
    private Resident resid;

    public ResidentCredential() {
    }

    public ResidentCredential(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
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
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResidentCredential)) {
            return false;
        }
        ResidentCredential other = (ResidentCredential) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.ResidentCredential[ username=" + username + " ]";
    }
    
}
