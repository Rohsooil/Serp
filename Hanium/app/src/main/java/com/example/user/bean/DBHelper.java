package com.example.user.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by food8 on 2017-08-28.
 */

public class DBHelper extends SQLiteOpenHelper {

    Context mcontext;

    public DBHelper(Context context) {
        super(context, "InnerDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists chatgroup ("
                + " group_id text, "
                + " participant_id text,"
                + " group_key text PRIMARY KEY);");
        db.execSQL("create table if not exists chattings ("
                + " sense_id text, "
                + " my_id text,"
                + " group_id text,"
                + " message text,"
                + " chat_time text,"
                + " chat_read text,"
                + " chat_id text PRIMARY KEY);");
        db.execSQL("create table if not exists friendinfo ("
                + " my_id text, "
                + " fri_id text,"
                + " fri_block text,"
                + " fri_key text PRIMARY KEY );");
        db.execSQL("create table if not exists sensekey ("
                + " sense_id integer PRIMARY KEY, "
                + " location text,"
                + " latitude text"
                + " longitude text"
                + " question text,"
                + " answer text,"
                + " sensetime text,"
                + " sender_id text,"
                + " my_id text"
                + ");");
        db.execSQL("create table if not exists user ("
                + " user_id integer PRIMARY KEY, "
                + " user_email text,"
                + " user_password text,"
                + " user_phone integer,"
                + " user_gender integer,"
                + " user_birth integer,"
                + " user_name text,"
                + " user_nickname text,"
                + " user_profile text);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FRIENDINFO");
        onCreate(db);
    }
}
