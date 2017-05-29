package com.example.amine.busniess_card;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amine on 27/05/2017.
 */

public class SqlLiteConnection extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BussniessCard.db";
    public static final String TABLE_NAME = "users";
    public static final String id = "ID";
    public static final String username = "USERNAME";
    public static final String password = "PASSWORD";
    public static final String phone = "PHONE";
    public static final String adress = "ADRESS";
    public static final String email = "EMAIL";

    public SqlLiteConnection(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+username+" TEXT,"+password+" TEXT,"+phone+" INTEGER,"+adress+" TEXT,"+email+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username1 , String password1 , String phone1 , String adress1 , String email1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(username , username1);
        contentVal.put(password , password1);
        contentVal.put(phone , phone1);
        contentVal.put(adress , adress1);
        contentVal.put(email , email1);
        long result = db.insert(TABLE_NAME , null , contentVal);

        if(result== -1) {
            return false;
        }
        else
            return true;
    }

    public boolean checkLogin(String username , String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        final Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE USERNAME='"+username+"' and PASSWORD='"+password+"'",null);
        if (cursor != null)
            return true;
        else
            return false;
    }
}
