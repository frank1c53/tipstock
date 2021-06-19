package com.organization.tipstock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "User.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserDetails(phone TEXT primary key,member TEXT,expdate TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists UserDetails");

    }


    public Boolean insertUserData(String phone, String member, String expdate) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phone", phone);
        contentValues.put("member", member);
        contentValues.put("expdate",expdate);
        long result = DB.insert("UserDetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public Boolean updateUserData(String phone, String member, String expdate) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("member", member);
        contentValues.put("expdate", expdate);
        Cursor cursor = DB.rawQuery("Select * from UserDetails where phone = ? ", new String[]{phone});
        if (cursor.getCount() > 0) {


            long result = DB.update("UserDetails", contentValues, "phone=?", new String[]{phone});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }


    public Boolean deleteData(String phone) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        Cursor cursor = DB.rawQuery("Select * from UserDetails where phone = ? ", new String[]{phone});
        if (cursor.getCount() > 0) {


            long result = DB.delete("UserDetails", "phone=?", new String[]{phone});

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }


    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();


        Cursor cursor = DB.rawQuery("Select * from UserDetails ", null);
        return cursor;
    }


}
