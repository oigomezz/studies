package com.example.directorio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class EnterpriseOperations {

    public static final String LOGTAG = "EMP_MNGMNT_SYS";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            EnterpriseDBHandler.COLUMN_ID,
            EnterpriseDBHandler.COLUMN_NAME,
            EnterpriseDBHandler.COLUMN_URL,
            EnterpriseDBHandler.COLUMN_PHONE,
            EnterpriseDBHandler.COLUMN_MAIL,
            EnterpriseDBHandler.COLUMN_PRODUCTS,
            EnterpriseDBHandler.COLUMN_CLASSIFICATION

    };

    public EnterpriseOperations(Context context){
        dbhandler = new EnterpriseDBHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();

    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }

    public Enterprise addEnterprise(Enterprise enterprise){
        ContentValues values  = new ContentValues();
        values.put(EnterpriseDBHandler.COLUMN_NAME,enterprise.getName());
        values.put(EnterpriseDBHandler.COLUMN_URL,enterprise.getUrl());
        values.put(EnterpriseDBHandler.COLUMN_MAIL, enterprise.getEmail());
        values.put(EnterpriseDBHandler.COLUMN_PHONE, enterprise.getPhone());
        values.put(EnterpriseDBHandler.COLUMN_PRODUCTS, enterprise.getProducts());
        values.put(EnterpriseDBHandler.COLUMN_CLASSIFICATION, enterprise.getClassification());

        long insertid = database.insert(EnterpriseDBHandler.TABLE_ENTERPRISES,null,values);
        enterprise.setId(insertid);
        return enterprise;

    }

    // Getting single Enterprise
    public Enterprise getEnterprise(long id) {
        Enterprise e;
        Cursor cursor = database.query(EnterpriseDBHandler.TABLE_ENTERPRISES,allColumns,EnterpriseDBHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null && cursor.getCount()>=1) {
            cursor.moveToFirst();
            e = new Enterprise(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        } else {
            e=null;
        }
        // return Enterprise
        return e;
    }

    public List<Enterprise> getAllEnterprises() {

        Cursor cursor = database.query(EnterpriseDBHandler.TABLE_ENTERPRISES,allColumns,null,null,null, null, null);

        List<Enterprise> enterprises= new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Enterprise enterprise= new Enterprise();
                enterprise.setId(cursor.getLong((cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_ID))));
                enterprise.setName(cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_NAME)));
                enterprise.setUrl(cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_URL)));
                enterprise.setEmail(cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_MAIL)));
                enterprise.setPhone(cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_PHONE)));
                enterprise.setProducts(cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_PRODUCTS)));
                enterprise.setClassification(cursor.getString(cursor.getColumnIndex(EnterpriseDBHandler.COLUMN_CLASSIFICATION)));
                enterprises.add(enterprise);
            }
        }
        // return All Enterprises
        return enterprises;
    }

    // Updating Enterprise
    public int updateEnterprise(Enterprise enterprise) {

        ContentValues values = new ContentValues();
        values.put(EnterpriseDBHandler.COLUMN_NAME, enterprise.getName());
        values.put(EnterpriseDBHandler.COLUMN_URL, enterprise.getUrl());
        values.put(EnterpriseDBHandler.COLUMN_MAIL, enterprise.getEmail());
        values.put(EnterpriseDBHandler.COLUMN_PHONE, enterprise.getPhone());
        values.put(EnterpriseDBHandler.COLUMN_PRODUCTS, enterprise.getProducts());
        values.put(EnterpriseDBHandler.COLUMN_CLASSIFICATION, enterprise.getClassification());

        // updating row
        return database.update(EnterpriseDBHandler.TABLE_ENTERPRISES, values,
                EnterpriseDBHandler.COLUMN_ID + "=?",new String[] { String.valueOf(enterprise.getId())});
    }

    // Deleting Enterprise
    public void removeEnterprise(Enterprise enterprise) {
        database.delete(EnterpriseDBHandler.TABLE_ENTERPRISES, EnterpriseDBHandler.COLUMN_ID + "=" + enterprise.getId(), null);
    }

}
