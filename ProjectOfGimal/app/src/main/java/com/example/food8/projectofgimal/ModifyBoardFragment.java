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
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by food8 on 2017-06-15.
 */

public class ModifyBoardFragment extends Fragment {
    ViewGroup v;
    SQLiteDatabase sqldb;
    DbHelper dbHelper;
    Button modifyback, modifyok;
    EditText modifycontent;
    String id;
    String pw;
    String position;
    String modifycontent2;
    String check1;
    int check2;
    String check3;
    MainActivity mainActivity;

    String savetitle;
    String savecontent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.modifyboard_fragment,container,false);
        mainActivity = (MainActivity) getActivity();
        id = getArguments().getString("id");
        position = getArguments().getString("position");

        modifyback = (Button) v.findViewById(R.id.modifyback);
        modifyok = (Button) v.findViewById(R.id.modifyok);
        modifycontent = (EditText) v.findViewById(R.id.modifycontent);


        modifyok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifycontent2 = modifycontent.getText().toString();
                dbHelper = new DbHelper(getApplicationContext());
                sqldb = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqldb.rawQuery("SELECT * FROM boardTBL", null);
                while (cursor.moveToNext()){
                    if(position.equals(cursor.getString(1))){
                        savetitle=cursor.getString(2);
                        savecontent=cursor.getString(3);
                    }
                }
                sqldb.execSQL("DELETE from boardTBL where boardnumber='"+position+"';");
                cursor.moveToFirst();
                while (cursor.moveToNext()) {
                    check1=cursor.getString(1);
                    check2=Integer.parseInt(check1);
                    check3=String.valueOf(check2);
                    if(check2>Integer.parseInt(position)){
                        check2--;
                        check1=String.valueOf(check2);
                        sqldb.execSQL("update boardTBL set boardnumber = '"+check1+"' where boardnumber = '"+check3+"' ;");
                    }

                }
                SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm", Locale.KOREA);
                String savedate = df.format(new Date());
                sqldb.execSQL("INSERT INTO boardTBL VALUES ( '" + id + "' , '" + check3 + "' , '" + savetitle + "' , '" + modifycontent2 + "' , '" + savedate + "' );");

                mainActivity.goBackBoard(id);
                Toast.makeText(getApplicationContext(),"게시글이 수정되었습니다.",Toast.LENGTH_LONG).show();
            }
        });

        modifyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"취소 하셨습니다. 게시판으로 돌아갑니다.",Toast.LENGTH_LONG).show();
                mainActivity.goBackBoard(id);
            }
        });
        return v;
    }
}
