package com.example.food8.projectofgimal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by food8 on 2017-06-15.
 */

public class BoardLookFragment extends Fragment {
    ViewGroup v;
    SQLiteDatabase sqldb;
    DbHelper dbHelper;
    Button delete,modification,boardback;
    String id=" ",id2=" ";
    String pw;
    TextView boardcontent,boardid,boarddate;
    String position;
    String content;
    String date;
    int check=0;
    String check1,check3;
    int check2;
    int num=0;
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.boardlook_fragment,container,false);
        /*받아와야댐ㅁ*/
        position = getArguments().getString("position");
        id = getArguments().getString("id");

        mainActivity = (MainActivity) getActivity();
        num=Integer.parseInt(position);
        num = num +1;
        position=String.valueOf(num);
        boardcontent=(TextView) v.findViewById(R.id.boardcontent);
        boardid=(TextView) v.findViewById(R.id.boardid);
        boarddate=(TextView) v.findViewById(R.id.boarddate);
        modification=(Button) v.findViewById(R.id.modification);
        delete=(Button) v.findViewById(R.id.delete);
        boardback=(Button) v.findViewById(R.id.boardback);

        boardback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goBackBoard(id);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbHelper = new DbHelper(getApplicationContext());
                sqldb = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqldb.rawQuery("SELECT * FROM boardTBL", null);
                sqldb.execSQL("DELETE from boardTBL where boardnumber='"+position+"';");
                cursor.moveToFirst();
                while (cursor.moveToNext()) {

                    check1 = cursor.getString(1);
                    check2 = Integer.parseInt(check1);
                    check3 = String.valueOf(check2);
                    if (check2 > Integer.parseInt(position)) {
                        check2--;
                        check1 = String.valueOf(check2);
                        sqldb.execSQL("update boardTBL set boardnumber = '" + check1 + "' where boardnumber = '" + check3 + "' ;");
                    }
                }
                Toast.makeText(getApplicationContext(),"해당 게시물이 삭제되었습니다.",Toast.LENGTH_LONG).show();
                mainActivity.goBackBoard(id);
            }
        });

        modification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),id+position,Toast.LENGTH_LONG).show();
                mainActivity.goModifyBoard(id,position);
            }
        });

        dbHelper = new DbHelper(getApplicationContext());
        //dbHelper.onUpgrade(sqldb,2,3);
        sqldb = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqldb.rawQuery("SELECT * FROM boardTBL", null);
        while (cursor.moveToNext()) {

            if(position.equals(cursor.getString(1))){
                content=cursor.getString(3);
                date="날짜 : "+cursor.getString(4);
                id2=cursor.getString(0);
                if(id.equals(id2)){
                    check=1;
                }
            }
        }
        sqldb.close();
        cursor.close();
        boardcontent.setText(content);
        boardid.setText(id2);
        boarddate.setText(date);
        if(!id.equals(id2)){
            delete.setVisibility(View.INVISIBLE);
            modification.setVisibility(View.INVISIBLE);
        }

        return v;
    }
}
