package com.example.amine.busniess_card;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Amine on 27/05/2017.
 */

public final class SqlLiteConnection extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BussniessCard.db";
    public static final String TABLE_NAME = "users";
    public static final String id = "ID";
    public static final String username = "USERNAME";
    public static final String password = "PASSWORD";
    public static final String job = "JOB";
    public static final String phone = "PHONE";
    public static final String adress = "ADRESS";
    public static final String email = "EMAIL";
    public static final String picture = "PICTURE";

    public static final String TABLE_CONTACTS = "contacts";
    public static final String idContact = "ID";
    public static final String nameContact = "NAME";
    public static final String jobContact = "JOB";
    public static final String mobilePhone = "MOBILEPHONE";
    public static final String adressContact = "ADRESS";
    public static final String emailContact = "EMAIL";


    public SqlLiteConnection(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+username+" TEXT,"+password+" TEXT,"+job+" TEXT,"+phone+" INTEGER,"+adress+" TEXT,"+email+" TEXT,"+picture+" TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_CONTACTS+" ("+idContact+" TEXT PRIMARY KEY,"+nameContact+" TEXT,"+jobContact+" TEXT,"+mobilePhone+" INTEGER,"+adressContact+" TEXT,"+emailContact+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_CONTACTS);
        onCreate(db);
    }

    public boolean insertData(String username1 , String password1 ,String job1, String phone1 , String adress1 , String email1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(username , username1);
        contentVal.put(password , password1);
        contentVal.put(job , job1);
        contentVal.put(phone , phone1);
        contentVal.put(adress , adress1);
        contentVal.put(email , email1);
        contentVal.put(picture , "not Provided");
        long result = db.insert(TABLE_NAME , null , contentVal);

        if(result== -1) {
            return false;
        }
        else
            return true;
    }

    public boolean checkRegisterUser(String username)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        /*Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE USERNAME='"+username+"'",null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;*/

        Cursor c = db.query(    SqlLiteConnection.TABLE_NAME,
                new String[]{   SqlLiteConnection.username, SqlLiteConnection.password,SqlLiteConnection.job,
                        SqlLiteConnection.phone, SqlLiteConnection.adress,
                        SqlLiteConnection.email,SqlLiteConnection.picture},
                SqlLiteConnection.username + " = ?",
                new String[] { username },
                null, null, null, null);
        if (c.getCount() > 0) {
            return true;
        }
        else
            return false;
    }

    public boolean checkLogin(String username , String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(    SqlLiteConnection.TABLE_NAME,
                new String[]{   SqlLiteConnection.username, SqlLiteConnection.password,SqlLiteConnection.job,
                        SqlLiteConnection.phone, SqlLiteConnection.adress,
                        SqlLiteConnection.email,SqlLiteConnection.picture},
                SqlLiteConnection.username + " = ? AND "+SqlLiteConnection.password+ " = ?",
                new String[] { username , password},
                null, null, null, null);

        if (c.getCount() > 0) {
            /*c.moveToFirst();
            Card u = new Card(mail, c.getString(0), c.getString(1), c.getString(2));
            u.setAddress(c.getString(3));
            u.setTel1(c.getString(4));
            u.setTel2(c.getString(5));
            u.setWebsite(c.getString(6));
            u.setPhoto(c.getString(7));
            c.close(); // close the cursor
            return u;*/
            return true;
        }
        else
            return false;
    }

    public BusniessCard getCard(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        BusniessCard bc = new BusniessCard();
        Cursor c = db.query(    SqlLiteConnection.TABLE_NAME,
                new String[]{   SqlLiteConnection.username, SqlLiteConnection.phone,SqlLiteConnection.job,
                        SqlLiteConnection.adress, SqlLiteConnection.email,SqlLiteConnection.picture},
                SqlLiteConnection.username + " = ?",
                new String[] { username },
                null, null, null, null);

        if (c.getCount() > 0) {
            c.moveToFirst();
            bc.setmName(c.getString(0));
            bc.setmPhoneNumber(c.getString(1));
            bc.setmJobTitle(c.getString(2));
            bc.setmAddress(c.getString(3));
            bc.setmEmail(c.getString(4));
            bc.setmPicture(c.getString(5));
            c.close(); // close the cursor
        }
        return bc;
    }

    public boolean insertContact(String idContact1 , String nameContact1 , String jobContact1 , String mobilePhone1 , String adressContact1 , String emailContact1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(idContact , idContact1);
        contentVal.put(nameContact , nameContact1);
        contentVal.put(jobContact , jobContact1);
        contentVal.put(mobilePhone , mobilePhone1);
        contentVal.put(adressContact , adressContact1);
        contentVal.put(emailContact , emailContact1);
        long result = db.insert(TABLE_CONTACTS , null , contentVal);

        if(result== -1) {
            return false;
        }
        else
            return true;
    }

    public boolean updateData(String username1 , String password1 ,String job1, String phone1 , String adress1 , String email1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(username , username1);
        contentVal.put(password , password1);
        contentVal.put(job , job1);
        contentVal.put(phone , phone1);
        contentVal.put(adress , adress1);
        contentVal.put(email , email1);
        contentVal.put(picture , "not Provided");
        db.update(TABLE_NAME ,contentVal,SqlLiteConnection.username+" = ?",new String[] { username1 });

        return true;
    }
}
