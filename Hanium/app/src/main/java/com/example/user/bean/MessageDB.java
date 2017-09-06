package com.example.user.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TaeYoung on 2017-08-08.
 */

public class MessageDB extends SQLiteOpenHelper{
    public MessageDB(Context context) {
        super(context, "groupDB1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE messageTBL2 ( id TEXT NOT NULL , content TEXT NOT NULL, date TEXT NOT NULL, " +
                "limit2 TEXT NOT NULL, latitude TEXT NOT NULL, longitude TEXT NOT NULL);");
        db.execSQL("INSERT INTO messageTBL2 VALUES ( '정태용' , '우리 처음 만난 날' , '17-04-13 12:22' , '우리 처음 만난 날' , '37.23123', '127.21312');");
        db.execSQL("INSERT INTO messageTBL2 VALUES ( '박소은' , '생일축하해!!ㅎㅎ' , '17-05-17 06:03' , '생일축하해!!ㅎㅎ' , '37.23123', '127.21312');");
        db.execSQL("INSERT INTO messageTBL2 VALUES ( '조아진' , '햄버거 5개' , '17-07-22 01:02' , '햄버거 5개' , '37.23123', '127.21312');");
        db.execSQL("INSERT INTO messageTBL2 VALUES ( '한상빈' , '아파용' , '17-08-13 10:43' , '아파용' , '37.23123', '127.21312');");
        db.execSQL("INSERT INTO messageTBL2 VALUES ( '노수일' , '흔들바위에서 한컷' , '17-09-01 11:22' , '흔들바위에서 한컷' , '37.23123', '127.21312');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS MessageTBL2");

        onCreate(db);
    }
}