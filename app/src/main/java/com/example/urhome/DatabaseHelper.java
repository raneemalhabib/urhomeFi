package com.example.urhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper{
    public static final String databaseName = "SignLog.db";
//db
    public static final String APART_TABLE = "Apart_Table";
    public static final String COLUMN_APART_NAME = "APART_NAME";
    public static final String COLUMN_APART_PRICE = "APART_PRICE";
    public static final String COLUMN_APART_LOCATION = "APART_LOCATION";
    public static final String COLUMN_APART_ROOMS = "APART_ROOMS";
    public static final String COLUMN_APART_RENT_EMAIL = "RENT_EMAIL";
    public static final String COLUMN_APART_OWNER_EMAIL = "OWNER_EMAIL";
    public static final String COLUMN_ID = "ID";





    public DatabaseHelper(@Nullable Context context) {
        super(context, "SignLog.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");
        String createTableStatement = "Create TABLE " + APART_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_APART_NAME + " TEXT, " +
                " " + COLUMN_APART_PRICE + " INT, " +COLUMN_APART_LOCATION + " TEXT, " + COLUMN_APART_ROOMS + " INT, " + COLUMN_APART_RENT_EMAIL + " TEXT, " +
                COLUMN_APART_OWNER_EMAIL + " TEXT ,FOREIGN KEY (" + COLUMN_APART_OWNER_EMAIL + ") REFERENCES " + "users(email))";
        MyDatabase.execSQL(createTableStatement);
    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }
    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }




    public boolean addOne(apart apartMod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_APART_NAME, apartMod.getName());
        cv.put(COLUMN_APART_PRICE, apartMod.getPrice());
        cv.put(COLUMN_APART_LOCATION, apartMod.getLocation());
        cv.put(COLUMN_APART_ROOMS, apartMod.getRooms());
        cv.put(COLUMN_APART_RENT_EMAIL,apartMod.getRentEmail());
        cv.put(COLUMN_APART_OWNER_EMAIL, MyApplication.getInstance().getUserEmail());
        long insert = db.insert(APART_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean DeleteOne(apart apartMod) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "Delete From " + APART_TABLE + " WHERE " + COLUMN_ID + " = " + apartMod.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            // nothing happens. no one is added.
            return false;
        }
        //close
    }

    public List<apart> getEveryone() {
        List<apart> returnList = new ArrayList<>();
        // get data from database
        String queryString = "Select * from " + APART_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            // loop through cursor results
            do {
                int AID = cursor.getInt(0);
                String AName = cursor.getString(1);
                int APrice = cursor.getInt(2);
                String ALocation = cursor.getString(3);
                int ARooms = cursor.getInt(4);
                String rentEmail = cursor.getString(5);
                apart newApart = new apart(AID, AName, APrice ,ALocation,ARooms,rentEmail);
                returnList.add(newApart);
            } while (cursor.moveToNext());
        } else {
            // nothing happens. no one is added.
        }
        //close
        cursor.close();
        db.close();
        return returnList;
    }

    public List<apart> getEveryoneWithoutRented() {
        String queryString = "Select * from " + APART_TABLE + " WHERE " + COLUMN_APART_RENT_EMAIL + " = ? " + "OR " + COLUMN_APART_RENT_EMAIL + " = ? "  ;
        String[] selectionArgs = { MyApplication.getInstance().getUserEmail(), "NOT RENTED YET"};
        return  getApartFromQuery(queryString,selectionArgs);
    }
    public List<apart> getMyApart(){
        String queryString = "Select * from " + APART_TABLE + " WHERE " + COLUMN_APART_OWNER_EMAIL + " = ? ";
        String[] selectionArgs = { MyApplication.getInstance().getUserEmail()};
        return  getApartFromQuery(queryString,selectionArgs);
    }


    List<apart> getApartFromQuery(String queryString, String[] selectionArgs){
        List<apart> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, selectionArgs);
        if (cursor.moveToFirst()) {
            // loop through cursor results
            do {
                int AID = cursor.getInt(0);
                String AName = cursor.getString(1);
                int APrice = cursor.getInt(2);
                String ALocation = cursor.getString(3);
                int ARooms = cursor.getInt(4);
                String rentEmail = cursor.getString(5);
                apart newApart = new apart(AID, AName, APrice ,ALocation,ARooms,rentEmail);
                returnList.add(newApart);
            } while (cursor.moveToNext());
        } else {
            // nothing happens. no one is added.
        }
        //close
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean updateApartment(int id, apart apart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_APART_NAME, apart.getName());
        cv.put(COLUMN_APART_PRICE, apart.getPrice());
        cv.put(COLUMN_APART_LOCATION, apart.getLocation());
        cv.put(COLUMN_APART_ROOMS, apart.getRooms());
        cv.put(COLUMN_APART_RENT_EMAIL,apart.getRentEmail());
        long insert = db.update(APART_TABLE, cv,  COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }




}
