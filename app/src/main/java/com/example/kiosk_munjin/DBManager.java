package com.example.kiosk_munjin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }



    public boolean insert(String usrname, String usrmobile, String usrphone, String usraddr,String addrtype,String aterm,String alesson,String acoupon,String adate) {
        if (userexist(usrname,usrmobile,adate)==true){
//            addNewUser();
            return false;
        }
        else {
            ContentValues contentValue = new ContentValues();
            contentValue.put(DatabaseHelper.USRNAME, usrname);
            contentValue.put(DatabaseHelper.USRMOBILE, usrmobile);
            contentValue.put(DatabaseHelper.USRPHONE, usrphone);
            contentValue.put(DatabaseHelper.USRADDR, usraddr);
            contentValue.put(DatabaseHelper.USRADDRTYPE, addrtype);
            contentValue.put(DatabaseHelper.USRTERM, aterm);
            contentValue.put(DatabaseHelper.USRLESSON, alesson);
            contentValue.put(DatabaseHelper.USRCOUPON, acoupon);
            contentValue.put(DatabaseHelper.USRDATE, adate);
            database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
            return true;
        }
    }


    public boolean insert_condition(String usrcondition, String adate) {

        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.USRCONDITION, usrcondition);
        contentValue.put(DatabaseHelper.USRDATE, adate);
        database.insert(DatabaseHelper.TABLE_NAME_NEW, null, contentValue);
        return true;
    }

    public boolean userexist (String usrname, String usrmobile, String usrdate) {
        String where = "usr_name=? AND usr_mobile=? AND usr_date=?";
        String[] args = {usrname,usrmobile,usrdate};
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USRNAME, DatabaseHelper.USRMOBILE, DatabaseHelper.USRPHONE, DatabaseHelper.USRADDR,
                DatabaseHelper.USRADDRTYPE,DatabaseHelper.USRTERM,DatabaseHelper.USRLESSON,DatabaseHelper.USRCOUPON,DatabaseHelper.USRDATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, where, args, null, null, null);
//        Cursor cursor = database.rawQuery("Select * from " + DatabaseHelper.TABLE_NAME,null);
//        Cursor cursor =  database.rawQuery( "select * from tbl_user where usr_name="+usrname+"", null );
//        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_NAME + " where usr_name=" +usrname+"", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USRNAME, DatabaseHelper.USRMOBILE, DatabaseHelper.USRPHONE, DatabaseHelper.USRADDR,
                DatabaseHelper.USRADDRTYPE,DatabaseHelper.USRTERM,DatabaseHelper.USRLESSON,DatabaseHelper.USRCOUPON,DatabaseHelper.USRDATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchone( String usrname,String usrmobile) {
        String where = "usr_name LIKE? OR usr_mobile LIKE?";
        String[] args = {"%"+usrname+"%","%"+usrmobile+"%"};
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USRNAME, DatabaseHelper.USRMOBILE, DatabaseHelper.USRPHONE, DatabaseHelper.USRADDR,
                DatabaseHelper.USRADDRTYPE,DatabaseHelper.USRTERM,DatabaseHelper.USRLESSON,DatabaseHelper.USRCOUPON,DatabaseHelper.USRDATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, where, args, null, null, DatabaseHelper.USRDATE+" DESC");
//        Cursor cursor = database.rawQuery("Select * from " + DatabaseHelper.TABLE_NAME,null);
//        Cursor cursor =  database.rawQuery( "select * from tbl_user where usr_name="+usrname+"", null );
//        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_NAME + " where usr_name=" +usrname+"", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor fetchone_condition(String condition) {
        String where = "usr_condition LIKE?";
        String[] args = {"%"+condition+"%"};
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USRCONDITION,DatabaseHelper.USRDATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME_NEW, columns, where, args, null, null, DatabaseHelper.USRDATE+" DESC");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String usrname, String usrmobile, String usrphone, String usraddr,String addrtype,String aterm,String alesson,String acoupon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.USRNAME, usrname);
        contentValues.put(DatabaseHelper.USRMOBILE, usrmobile);
        contentValues.put(DatabaseHelper.USRPHONE, usrphone);
        contentValues.put(DatabaseHelper.USRADDR, usraddr);
        contentValues.put(DatabaseHelper.USRADDRTYPE, addrtype);
        contentValues.put(DatabaseHelper.USRTERM, aterm);
        contentValues.put(DatabaseHelper.USRLESSON, alesson);
        contentValues.put(DatabaseHelper.USRCOUPON, acoupon);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

    public void deletename(String _name) {
        String where = "usr_name=?";
        String[] args = {_name};
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.USRNAME, DatabaseHelper.USRMOBILE, DatabaseHelper.USRPHONE, DatabaseHelper.USRADDR,
                DatabaseHelper.USRADDRTYPE,DatabaseHelper.USRTERM,DatabaseHelper.USRLESSON,DatabaseHelper.USRCOUPON,DatabaseHelper.USRDATE};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, where, args, null, null, null);
//        Cursor cursor = database.rawQuery("Select * from " + DatabaseHelper.TABLE_NAME,null);
//        Cursor cursor =  database.rawQuery( "select * from tbl_user where usr_name="+usrname+"", null );
//        Cursor cursor = database.rawQuery("select * from " + DatabaseHelper.TABLE_NAME + " where usr_name=" +usrname+"", null);
        if (cursor != null)
        {
            try
            {
                if (cursor.moveToFirst())
                {
                    do
                    {
                        int _id =  cursor.getInt(cursor.getColumnIndex("_id"));
                        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
                    } while (cursor.moveToNext());
                }
            }
            finally {cursor.close();}
        }
    }
}