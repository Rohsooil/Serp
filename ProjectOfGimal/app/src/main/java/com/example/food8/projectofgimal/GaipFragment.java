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
import android.widget.RadioButton;
import android.widget.Toast;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by food8 on 2017-06-09.
 */

public class GaipFragment extends Fragment {
    ViewGroup v;
    Button signOkBtn, signBackBtn;
    EditText signID, signPW, signName, signAge, signRetype;
    RadioButton radioman;
    RadioButton radiowoman;
    SQLiteDatabase sqldb;
    DbHelper dbHelper;
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.gaip_fragment,container,false);
        signID = (EditText) v.findViewById(R.id.SignID);
        signPW = (EditText) v.findViewById(R.id.SignPW);
        signRetype = (EditText) v.findViewById(R.id.SignRetype);
        signName = (EditText) v.findViewById(R.id.SignName);
        signAge = (EditText) v.findViewById(R.id.SignAge);
        signOkBtn = (Button) v.findViewById(R.id.SignOKBtn);
        signBackBtn = (Button) v.findViewById(R.id.SignBackBtn);
        radioman = (RadioButton) v.findViewById(R.id.radioman);
        radiowoman = (RadioButton) v.findViewById(R.id.radiowoman);
        mainActivity = (MainActivity) getActivity();

        signBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goTologin();
            }
        });

        signOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DbHelper(getApplicationContext());
                sqldb = dbHelper.getReadableDatabase();
                // dbHelper.onUpgrade(sqldb,2,3);
                Cursor cursor;
                cursor = sqldb.rawQuery("SELECT * FROM userTBL", null);
                String id = signID.getText().toString();
                String pw = signPW.getText().toString();
                String retype = signRetype.getText().toString();
                String name = signName.getText().toString();
                String age = signAge.getText().toString();
                String gender = "비공개";

                String check;
                int check2 = 0;

                if(!pw.equals(retype)){
                    check2 = 1;
                    Toast.makeText(getApplicationContext(), "비밀번호가 서로 다릅니다. 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                }
                if (radioman.isChecked()) {
                    gender = "남자";
                }
                if (radiowoman.isChecked()) {
                    gender = "여자";
                }

                while (cursor.moveToNext()) {
                    check = cursor.getString(0);
                    if (check.equals(id)) {
                        Toast.makeText(getApplicationContext(), "이미 있는 ID 입니다. 다른 아이디명을 입력 해주세요.", Toast.LENGTH_LONG).show();
                        check2 = 1;
                        break;
                    }
                }
                sqldb.close();
                cursor.close();

                if (check2 == 0) {
                    sqldb=dbHelper.getWritableDatabase();
                    dbHelper.onUpgrade(sqldb,2,3);
                    sqldb.execSQL("INSERT INTO userTBL VALUES ( '" + id + "' , '" + pw + "' , '" + age + "' , '" + name + "' , '" + gender + "' );");
                    sqldb.close();
                    Toast.makeText(getApplicationContext(), "회원가입을 완료 했습니다.", Toast.LENGTH_LONG).show();
                    mainActivity.goTologin();
                }
            }
        });

        return v;
    }
}
