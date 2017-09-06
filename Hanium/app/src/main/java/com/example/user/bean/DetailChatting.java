package com.example.user.bean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailChatting extends AppCompatActivity {
    Intent intent;
    TextView nameText;
    String chatter;
    EditText editText;
    ScrollView scrollView;
    ListView listView;
    Button button;
    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chatting);
        intent = getIntent();
        chatter = intent.getStringExtra("chat").toString();
        nameText = (TextView) findViewById(R.id.oppositeName);
        nameText.setText(chatter);
        editText = (EditText) findViewById(R.id.chatting);
        scrollView = (ScrollView) findViewById(R.id.contentScorll);
        listView = (ListView) findViewById(R.id.list_chat);
        button = (Button) findViewById(R.id.sendChat);
        adapter = new ArrayAdapter<String>(this, R.layout.chat_content_text, list);
        listView.setAdapter(adapter);

        //리스트뷰의 스크롤과 스크롤뷰의 스크롤이 겹치는것을 방지
        listView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                scrollView.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        //스크롤뷰의 스크롤이 안되게 방지
        //스크롤뷰는 editText를 누를 때 키보드에 의해서 가려지는 현상을 방지하기 위한용도. 스크롤이 필요 없음
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                list.add(str);
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });

    }
}
