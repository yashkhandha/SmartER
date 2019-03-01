package edu.monash.ykha0002.assignment2;


/**
 * Created by yashkhandha on 29/04/2018.
 */

public class ResidentCredential{

    private Resident resid;
    private String username;
    private String passwordhash;
    private String registrationdate;


    public ResidentCredential(Resident resid, String username, String passwordhash, String registrationdate) {
        this.resid = resid;
        this.username = username;
        this.passwordhash = passwordhash;
        this.registrationdate = registrationdate;
    }

    public ResidentCredential() {
    }

    public ResidentCredential(String username, String passwordhash, String registrationdate) {
        this.username = username;
        this.passwordhash = passwordhash;
        this.registrationdate = registrationdate;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
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

    public String getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(String registrationdate) {
        this.registrationdate = registrationdate;
    }

    @Override
    public String toString() {
        return "ResidentCredential{" +
                "resid=" + resid +
                ", username='" + username + '\'' +
                ", passwordhash='" + passwordhash + '\'' +
                ", registrationdate=" + registrationdate +
                '}';
    }
}
