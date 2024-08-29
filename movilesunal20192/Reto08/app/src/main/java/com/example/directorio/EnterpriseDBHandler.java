package com.example.directorio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class EnterpriseDBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "enterprise.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ENTERPRISES = "enterprises";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_PHONE= "phone";
    public static final String COLUMN_PRODUCTS= "products";
    public static final String COLUMN_CLASSIFICATION= "classification";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_ENTERPRISES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_URL + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_MAIL + " TEXT, " +
                    COLUMN_PRODUCTS + " TEXT, " +
                    COLUMN_CLASSIFICATION + " TEXT " +
                    ")";

    public EnterpriseDBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ENTERPRISES);
        db.execSQL(TABLE_CREATE);
    }
}
