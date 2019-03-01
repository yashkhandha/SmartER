package edu.monash.ykha0002.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by yashkhandha on 24/04/2018.
 */

public class DBHelperElectricityUsage extends SQLiteOpenHelper {

    // Set Database Properties
    public static final String DATABASE_NAME = "ElectricityUsageDB";
    public static final int DATABASE_VERSION = 1;

    //all columns of the table
    private String[] columns={
            ElectricityUsage.COLUMN_USAGEID,
            ElectricityUsage.COLUMN_RESID,
            ElectricityUsage.COLUMN_USAGEDATE,
            ElectricityUsage.COLUMN_USAGEHOUR,
            ElectricityUsage.COLUMN_FRIDGEUSAGE,
            ElectricityUsage.COLUMN_AIRCONDITIONERUSAGE,
            ElectricityUsage.COLUMN_WASHINGMACHINEUSAGE,
            ElectricityUsage.COLUMN_TEMPERATURE
    };

    public DBHelperElectricityUsage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelperElectricityUsage(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //CREATE
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ElectricityUsage.CREATE_STATEMENT);
    }

    //DROP
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ElectricityUsage.TABLE_NAME);
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Resident.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //INSERT
    public void addUsage(ElectricityUsage usage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ElectricityUsage.COLUMN_USAGEID, usage.getUsageid());
        values.put(ElectricityUsage.COLUMN_RESID, usage.getResid().getResid());
        values.put(ElectricityUsage.COLUMN_USAGEDATE, usage.getUsagedate());
        values.put(ElectricityUsage.COLUMN_USAGEHOUR, usage.getUsagehour());
        values.put(ElectricityUsage.COLUMN_FRIDGEUSAGE, usage.getFridgeusage());
        values.put(ElectricityUsage.COLUMN_AIRCONDITIONERUSAGE, usage.getAirconditionerusage());
        values.put(ElectricityUsage.COLUMN_WASHINGMACHINEUSAGE, usage.getWashingmachineusage());
        values.put(ElectricityUsage.COLUMN_TEMPERATURE, usage.getTemperature());

        db.insert(ElectricityUsage.TABLE_NAME, null, values);
        db.close();
    }

    //SELECT
    public Cursor getAllEntries(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(ElectricityUsage.TABLE_NAME, columns, null, null, null, null, null);
    }

    public HashMap<Integer, ElectricityUsage> getAllUsage() {
        HashMap<Integer, ElectricityUsage> u = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ElectricityUsage.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            ElectricityUsage usage = new ElectricityUsage(cursor.getInt(0),cursor.getString(2),cursor.getInt(3),cursor.getDouble(4),cursor.getDouble(5),cursor.getDouble(6),cursor.getDouble(7));
            u.put(usage.getUsageid(), usage);
        }
        cursor.close();
        db.close();
        if(u.size() == 0) {
            // If there are no people in the db then add some default people
        }
        return u;
    }

    //DELETE
    public void deleteUsage(ElectricityUsage usage){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ElectricityUsage.TABLE_NAME,
                null,
                null);
    }

    //UPDATE
    public void updateBook(ElectricityUsage usage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ElectricityUsage.COLUMN_USAGEID, usage.getUsageid());
        values.put(ElectricityUsage.COLUMN_RESID, usage.getResid().getResid());
        values.put(ElectricityUsage.COLUMN_USAGEDATE, usage.getUsagedate());
        values.put(ElectricityUsage.COLUMN_USAGEHOUR, usage.getUsagehour());
        values.put(ElectricityUsage.COLUMN_FRIDGEUSAGE, usage.getFridgeusage());
        values.put(ElectricityUsage.COLUMN_AIRCONDITIONERUSAGE, usage.getAirconditionerusage());
        values.put(ElectricityUsage.COLUMN_WASHINGMACHINEUSAGE, usage.getWashingmachineusage());
        values.put(ElectricityUsage.COLUMN_TEMPERATURE, usage.getTemperature());

        db.update(ElectricityUsage.TABLE_NAME,values, ElectricityUsage.COLUMN_USAGEID+"= ?",new String[]{String.valueOf(usage.getUsageid())});
        db.close();
    }
}
