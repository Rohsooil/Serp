package com.example.food8.projectofgimal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static java.lang.String.valueOf;

/**
 * Created by food8 on 2017-06-15.
 */

public class NoticeBoardFragment extends Fragment {
    ViewGroup v;
    SQLiteDatabase sqldb;
    DbHelper dbHelper;
    Button writeboard;
    String id;
    MainActivity mainActivity;
    ListView listview;
    List<String> oPerlishArray = new ArrayList<String>();
    List<String> oPerlishArray2 = new ArrayList<String>();
    int num=0;

    ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();

    HashMap<String,String> item;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.noticeboard_fragment, container, false);

        id = getArguments().getString("id");
        Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
        mainActivity = (MainActivity) getActivity();
        writeboard = (Button) v.findViewById(R.id.writeboard);
        listview = (ListView) v.findViewById(R.id.listview);
        dbHelper = new DbHelper(getApplicationContext());
        sqldb = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqldb.rawQuery("SELECT * FROM boardTBL", null);

        while (cursor.moveToNext()) {
            oPerlishArray.add(cursor.getString(2));
            oPerlishArray2.add(cursor.getString(4));
            item = new HashMap<String,String>();
            if (oPerlishArray.get(num)==null) {
                list.add(null); // 리스트에 추가될 값이 들어오지 않을 경우, null을 임의로 추가

            } else {
                item.put("item 1",oPerlishArray.get(num));
                item.put("item 2",oPerlishArray2.get(num));

                list.add(item); // 리스트에 정상적인 값을 추가
            }
            num++;
        }


        if(id.equals("admin")){
            writeboard.setVisibility(View.INVISIBLE);
        }



        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list, android.R.layout.simple_list_item_2,
                new String[] {"item 1","item 2"},
                new int[] {android.R.id.text1, android.R.id.text2});

        listview.setAdapter(simpleAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id2) {
                String position2;
                position2 = valueOf(position);
                mainActivity.goBoardLook(id,position2);
            }
        });
        sqldb.close();
        cursor.close();

        writeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goWirteBoard(id);
            }
        });
        return v;
    }
}
