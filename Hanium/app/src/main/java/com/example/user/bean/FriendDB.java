package com.example.user.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by food8 on 2017-07-28.
 */

public class FriendDB extends SQLiteOpenHelper {

    private Context context;

    public FriendDB(Context context) {
        super(context,"FriendDB", null, 1);
        //this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" CREATE TABLE FRIEND ( ");
        stringBuffer.append(" NAME TEXT NOT NULL, EMAIL NOT NULL, PHONE NOT NULL, PROFILE TEXT NOT NULL, " +
                "NICKNAME TEXT NOT NULL, ISBLOCK " + "INTEGER NOT NULL, ID NOT NULL )");
        db.execSQL(stringBuffer.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void testDB(){
        SQLiteDatabase db = getReadableDatabase();
    }
}
