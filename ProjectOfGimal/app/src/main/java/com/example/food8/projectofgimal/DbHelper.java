package com.example.food8.projectofgimal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DbHelper extends SQLiteOpenHelper{
    public DbHelper(Context context) {
        super(context, "groupDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE  userTBL ( user_id TEXT NOT NULL , user_pw TEXT NOT NULL, user_age TEXT NOT NULL, user_name TEXT NOT NULL, user_gender TEXT NOT NULL);");
        db.execSQL("CREATE TABLE  boardTBL ( user_id TEXT NOT NULL , boardnumber TEXT NOT NULL, title TEXT NOT NULL, content TEXT NOT NULL, date TEXT NOT NULL);");
        db.execSQL("INSERT INTO boardTBL VALUES ( 'admin' , '1' , '공지사항 입니다' , '타인에게 상처주는 언행은 삼갑시다.' , '2017-06-15 05:22' );");
        db.execSQL("INSERT INTO userTBL VALUES ( 'admin' , '1' , '1' , '관리자' , '1' );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS userTBL");
        db.execSQL("DROP TABLE IF EXISTS boardTBL");
        onCreate(db);
    }
}