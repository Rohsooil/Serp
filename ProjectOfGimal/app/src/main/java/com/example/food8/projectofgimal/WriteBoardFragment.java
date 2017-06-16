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

public class WriteBoardFragment extends Fragment {
    ViewGroup v;
    SQLiteDatabase sqldb;
    DbHelper dbHelper;
    EditText title, content;
    Button writeBackBtn, writeOkBtn;
    String id;
    String check1;
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.writeboard_fragment, container, false);
        mainActivity = (MainActivity) getActivity();

        id = getArguments().getString("id");


        title = (EditText) v.findViewById(R.id.title);
        content = (EditText) v.findViewById(R.id.content);
        writeBackBtn = (Button) v.findViewById(R.id.writeBackBtn);
        writeOkBtn = (Button) v.findViewById(R.id.writeOkBtn);

        writeOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = 0;
                String title1 = "none";
                String content1 = "none";
                title1 = title.getText().toString();
                content1 = content.getText().toString();
                if (title1.equals("") || content1.equals("")) {
                    Toast.makeText(getApplicationContext(), "제목과 내용을 모두 입력해주세요", Toast.LENGTH_LONG).show();
                } else {
                    SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm", Locale.KOREA);
                    String date = df.format(new Date());

                    dbHelper = new DbHelper(getApplicationContext());
                    sqldb = dbHelper.getWritableDatabase();
                    Cursor cursor;
                    cursor = sqldb.rawQuery("SELECT * FROM boardTBL", null);


                    while (cursor.moveToNext()) {

                        check1 = cursor.getString(1);

                        check = Integer.parseInt(check1);
                    }
                    check++;
                    check1 = String.valueOf(check);
                    sqldb.close();
                    cursor.close();
                    sqldb = dbHelper.getWritableDatabase();
                    sqldb.execSQL("INSERT INTO boardTBL VALUES ( '" + id + "' , '" + check1 + "' , '" + title1 + "' , '" + content1 + "' , '" + date + "' );");
                    sqldb.close();

                    mainActivity.goBackBoard(id);

                }
            }
        });
        writeBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goBackBoard(id);
            }
        });

        return v;
    }
}
