package edu.monash.ykha0002.assignment2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yashkhandha on 24/04/2018.
 */

public class ElectricityUsage implements Parcelable {

    private int usageid;
    private Resident resid;
    private String usagedate;
    private int usagehour;
    private double fridgeusage;
    private double airconditionerusage;
    private double washingmachineusage;
    private double temperature;

    //Database constants
    public static final String TABLE_NAME = "ElectricityUsage";
    public static final String COLUMN_USAGEID = "usageid";
    public static final String COLUMN_RESID = "resid";
    public static final String COLUMN_USAGEDATE = "usagedate";
    public static final String COLUMN_USAGEHOUR = "usagehour";
    public static final String COLUMN_FRIDGEUSAGE = "fridgeusage";
    public static final String COLUMN_AIRCONDITIONERUSAGE = "airconditionerusage";
    public static final String COLUMN_WASHINGMACHINEUSAGE = "washingmachineusage";
    public static final String COLUMN_TEMPERATURE = "temperature";

    // Table create statement
    public static final String CREATE_STATEMENT = "CREATE TABLE "
            + TABLE_NAME + "(" +
            COLUMN_USAGEID + " INTEGER NOT NULL, " +
            COLUMN_RESID + " INTEGER NOT NULL, " +
            COLUMN_USAGEDATE + " TEXT NOT NULL, " +
            COLUMN_USAGEHOUR + " INTEGER NOT NULL, " +
            COLUMN_FRIDGEUSAGE + " REAL NOT NULL, " +
            COLUMN_AIRCONDITIONERUSAGE + " REAL NOT NULL, " +
            COLUMN_WASHINGMACHINEUSAGE + " REAL NOT NULL, " +
            COLUMN_TEMPERATURE + " REAL " +
            ")";

    //Parcel constructor
    protected ElectricityUsage(Parcel in) {
        usageid = in.readInt();
        usagedate = in.readString();
        usagehour = in.readInt();
        fridgeusage = in.readFloat();
        airconditionerusage = in.readFloat();
        washingmachineusage = in.readFloat();
        temperature = in.readFloat();
    }

    //default constructor
    public ElectricityUsage(int anInt, String string, int cursorInt, double aDouble, double cursorDouble, double v, double aDouble1){

    }

    //Parametrized constructor
    public ElectricityUsage(int usageid, Resident resid, String usagedate, int usagehour, double fridgeusage, double airconditionerusage, double washingmachineusage, double temperature) {
        this.usageid = usageid;
        this.resid = resid;
        this.usagedate = usagedate;
        this.usagehour = usagehour;
        this.fridgeusage = fridgeusage;
        this.airconditionerusage = airconditionerusage;
        this.washingmachineusage = washingmachineusage;
        this.temperature = temperature;
    }

    //Parametrized constructor
    public ElectricityUsage(int usageid, String cdate, int currentTimeHHInt, Double d1, Double d2, Double d3) {
        this.usageid = usageid;
        this.usagedate = cdate;
        this.usagehour = currentTimeHHInt;
        this.fridgeusage = d1;
        this.airconditionerusage = d2;
        this.washingmachineusage = d3;
    }


    public static final Creator<ElectricityUsage> CREATOR = new Creator<ElectricityUsage>() {
        @Override
        public ElectricityUsage createFromParcel(Parcel in) {
            return new ElectricityUsage(in);
        }

        @Override
        public ElectricityUsage[] newArray(int size) {
            return new ElectricityUsage[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public int getUsageid() {
        return usageid;
    }

    public void setUsageid(int usageid) {
        this.usageid = usageid;
    }

    public Resident getResid() {
        return resid;
    }

    public void setResid(Resident resid) {
        this.resid = resid;
    }

    public String getUsagedate() {
        return usagedate;
    }

    public void setUsagedate(String usagedate) {
        this.usagedate = usagedate;
    }

    public int getUsagehour() {
        return usagehour;
    }

    public void setUsagehour(int usagehour) {
        this.usagehour = usagehour;
    }

    public double getFridgeusage() {
        return fridgeusage;
    }

    public void setFridgeusage(double fridgeusage) {
        this.fridgeusage = fridgeusage;
    }

    public double getAirconditionerusage() {
        return airconditionerusage;
    }

    public void setAirconditionerusage(double airconditionerusage) {
        this.airconditionerusage = airconditionerusage;
    }

    public double getWashingmachineusage() {
        return washingmachineusage;
    }

    public void setWashingmachineusage(double washingmachineusage) {
        this.washingmachineusage = washingmachineusage;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
