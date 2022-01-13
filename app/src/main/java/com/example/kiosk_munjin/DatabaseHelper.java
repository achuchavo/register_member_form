package com.example.kiosk_munjin;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "tbl_user";
    public static final String TABLE_NAME_NEW = "tbl_condition";

    // Table columns
    public static final String _ID = "_id";
    public static final String USRNAME = "usr_name";
    public static final String USRMOBILE = "usr_mobile";
    public static final String USRPHONE = "usr_phone";
    public static final String USRADDR = "usr_addr";
    public static final String USRADDRTYPE = "usr_addrtype";
    public static final String USRTERM = "usr_term";
    public static final String USRLESSON = "usr_lesson";
    public static final String USRCOUPON = "usr_coupon";
    public static final String USRDATE = "usr_date";
    public static final String USRCONDITION = "usr_condition";

    // Database Information
    static final String DB_NAME = "munjin.DB";

    // database version
    static final int DB_VERSION = 16;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USRNAME + " TEXT NOT NULL, " + USRMOBILE + " TEXT, "+
            USRPHONE + " TEXT, "+ USRADDR + " TEXT,"+ USRADDRTYPE + " TEXT, "+
            USRTERM + " TEXT, "+USRLESSON + " TEXT, " + USRCOUPON + " TEXT, " + USRDATE +" TEXT);";

    private static final String CREATE_TABLE_NEW = "create table " + TABLE_NAME_NEW + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USRCONDITION + " TEXT,"  + USRDATE +" TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_NEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        if (i1 > i) {
//            sqLiteDatabase.execSQL(CREATE_TABLE);
//            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME +" ADD COLUMN " + USRLESSON +" TEXT");
//            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME +" ADD COLUMN " + USRCOUPON +" TEXT");
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_NEW);
//            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
//            sqLiteDatabase.execSQL(CREATE_TABLE_NEW);
//            sqLiteDatabase.execSQL(CREATE_TABLE);
//            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME +" ADD COLUMN " + USRDATE +" TEXT");
//            sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME_NEW +" ADD COLUMN " + USRDATE +" TEXT");

        }
//        onCreate(sqLiteDatabase);

    }


    //open sign activity
    public void addtable(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(CREATE_TABLE_NEW);
    }
}
