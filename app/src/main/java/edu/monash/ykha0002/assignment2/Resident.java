package edu.monash.ykha0002.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by yashkhandha on 24/04/2018.
 */

public class Resident implements Parcelable {

    private int resid;
    private String firstname;
    private String surname;
    private String dateofbirth;
    private String address;
    private String postcode;
    private String email;
    private String mobilenumber;
    private int numberofresidents;
    private String energyprovider;

    //Parcel constructor
    protected Resident(Parcel in) {
        resid = in.readInt();
        firstname = in.readString();
        surname = in.readString();
        address = in.readString();
        postcode = in.readString();
        email = in.readString();
        mobilenumber = in.readString();
        numberofresidents = in.readInt();
        energyprovider = in.readString();
    }

    //default constructor
    public Resident() {
    }

    //Parametrized constructor
    public Resident(int resID, String firstname, String surname, String dateofbirth, String address, String postcode, String email, String mobilenumber, int numberofresidents, String energyprovider) {
        this.resid = resID;
        this.firstname = firstname;
        this.surname = surname;
        this.dateofbirth = dateofbirth;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.numberofresidents = numberofresidents;
        this.energyprovider = energyprovider;
    }

    public Resident(String firstname, String surname, String dateofbirth, String address, String postcode, String email, String mobilenumber, int numberofresidents, String energyprovider) {
        this.firstname = firstname;
        this.surname = surname;
        this.dateofbirth = dateofbirth;
        this.address = address;
        this.postcode = postcode;
        this.email = email;
        this.mobilenumber = mobilenumber;
        this.numberofresidents = numberofresidents;
        this.energyprovider = energyprovider;
    }

    public Resident(int resid, String email, String address, String postcode, String firstname) {
        this.resid = resid;
        this.email = email;
        this.address = address;
        this.postcode = postcode;
        this.firstname = firstname;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(resid);
        parcel.writeString(firstname);
        parcel.writeString(surname);
        parcel.writeString(address);
        parcel.writeString(postcode);
        parcel.writeString(email);
        parcel.writeString(mobilenumber);
        parcel.writeInt(numberofresidents);
        parcel.writeString(energyprovider);
    }

    public static final Creator<Resident> CREATOR = new Creator<Resident>() {
        @Override
        public Resident createFromParcel(Parcel in) {
            return new Resident(in);
        }

        @Override
        public Resident[] newArray(int size) {
            return new Resident[size];
        }
    };

    //getter and setter for all the attributes

    public int getResid() {
        return resid;
    }

    public void setResid(int resid) {
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

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
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

    public int getNumberofresidents() {
        return numberofresidents;
    }

    public void setNumberofresidents(int numberofresidents) {
        this.numberofresidents = numberofresidents;
    }

    public String getEnergyprovider() {
        return energyprovider;
    }

    public void setEnergyprovider(String energyprovider) {
        this.energyprovider = energyprovider;
    }
}

