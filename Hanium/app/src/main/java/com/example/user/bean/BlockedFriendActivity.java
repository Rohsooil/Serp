package com.example.user.bean;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by food8 on 2017-08-14.
 */

public class BlockedFriendActivity extends AppCompatActivity {

    private static String TAG = "allselectuser";
    private static final String TAG_JSON="allselectuser";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_USER_EMAIL = "user_email";
    private static final String TAG_USER_PASSWORD = "user_password";
    private static final String TAG_USER_PHONE = "user_phone";
    private static final String TAG_USER_GENDER = "user_gender";
    private static final String TAG_USER_BIRTH = "user_birth";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_USER_NICKNAME = "user_nickname";
    private static final String TAG_USER_PROFILE = "user_profile";

    TextView blockedText;
    CheckBox checkBox;
    FriendDB friendDB;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Friend> friendList;
    ArrayList<Friend> blockedList;
    ListView listView;
    MyAdapter adapter;
    String mJsonString;
    Friend blockedFriend;
    private Handler handler;
    String my_id ="";

    Button releaseBtn, cancelBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blocked_friend_page);
        blockedText = (TextView) findViewById(R.id.blockedName);
        releaseBtn = (Button) findViewById(R.id.releasebtn);
        cancelBtn = (Button) findViewById(R.id.cancelbtn);

        Intent intent = getIntent();
        my_id = intent.getStringExtra("my_id");

        friendList = new ArrayList<Friend>();
        blockedList = new ArrayList<Friend>();
        friendDB = new FriendDB(getApplicationContext());
        sqLiteDatabase = friendDB.getReadableDatabase();
        //쿼리문으로 FRIEND 테이블을 불러온다.
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM FRIEND WHERE ISBLOCK = 1", null);
        while(cursor.moveToNext()){
            boolean isCurrentUser = false;
            String friendName = cursor.getString(0);
            String friendMail = cursor.getString(1);
            String friendPhone = cursor.getString(2);
            String friendProfile = cursor.getString(3);
            String friendNickname = cursor.getString(4);
            String innerId = cursor.getString(6);

            if(innerId.equals(my_id)){
                isCurrentUser = true;
            }

            if(isCurrentUser) {
                friendList.add(new Friend(friendName, friendMail, friendPhone, friendProfile, friendNickname, 1));
            }
        }
        //리스트뷰를 만들고 어뎁터에 추가해준다.
        listView = (ListView) findViewById(R.id.blockedPageList);
        adapter = new MyAdapter(getApplicationContext(), R.layout.list_blocked_friend,friendList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = friendList.get(position);
                if(friend.getIsBlock() == 0){
                    friend.setIsBlock(1);
                    Toast.makeText(getApplicationContext(),friend.getName()+" 해제됨",Toast.LENGTH_LONG).show();
                }else {
                    friend.setIsBlock(0);
                    Toast.makeText(getApplicationContext(),friend.getName()+" 선택됨",Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = adapter.getCount();
                for(int i = 0; i<count;i++) {
                    if(friendList.get(i).getIsBlock() == 0) {
                        Toast.makeText(getApplicationContext(), "ㅁㄴㅇㄹ"+friendList.get(i).getName(), Toast.LENGTH_LONG).show();
                    }
                    //friendList.remove(i);
                }
                finish();
            }
        });

        releaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog;
                int count = adapter.getCount();
                for(int i = 0; i<count;i++) {
                    if(friendList.get(i).getIsBlock() == 0) {
                        blockedList.add(friendList.get(i));
                    }
                }
                AllSelectUser task = new AllSelectUser();
                task.execute("http://211.253.30.146:7275/allselectuser.php");
                /*sqLiteDatabase.close();
                adapter.notifyDataSetChanged();*/
                progressDialog = ProgressDialog.show(BlockedFriendActivity.this, "Please Wait", null, true, true);
                handler = new Handler(); //외부 뷰를 내부로 설정하기 위한 핸들러 객체 설정

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        finish();
                    }
                }, 1000);

            }
        });
    }

    class MyAdapter extends BaseAdapter {
        Context mcontext;
        int list_friend;
        ArrayList<Friend> afriendList = new ArrayList<Friend>();
        LayoutInflater layoutInflater;
        boolean ischecked = true;
        BlockedFriendLayout blockedFriendLayout = null;

        public MyAdapter(Context context, int list_friend, ArrayList<Friend> friendList){
            mcontext = context;
            this.list_friend = list_friend;
            this.afriendList = friendList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return friendList.size();
        }

        @Override
        public Object getItem(int position) {
            return friendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                blockedFriendLayout = new BlockedFriendLayout(mcontext);
                //layoutInflater.inflate(R.layout.list_blocked_friend,blockedFriendLayout,true);
            } else {
                blockedFriendLayout = (BlockedFriendLayout) convertView;
            }

            Friend friend = friendList.get(position);
            blockedFriendLayout.setName(friend.getName());
            return blockedFriendLayout;
        }

    }

    private class UnBlockFriend extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(BlockedFriendActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String my_id = (String)params[0];
            String fri_id = (String)params[1];

            String serverURL = "http://211.253.30.146:7275/unblockfriend.php";
            String postParameters = "my_id=" + my_id + "&fri_id=" + fri_id;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString();
            } catch (Exception e) {
                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }
        }
    }

    private class AllSelectUser extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(BlockedFriendActivity.this, "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){
            }
            else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String user_id = item.getString(TAG_USER_ID);
                String user_mail = item.getString(TAG_USER_EMAIL);

                for(Friend friend : blockedList){
                    if(friend.getMail().equals(user_mail)){
                        friendDB = new FriendDB(getApplicationContext());
                        sqLiteDatabase = friendDB.getWritableDatabase();
                        Toast.makeText(getApplicationContext(), "호출은일단도미" + friend.getName(), Toast.LENGTH_LONG).show();
                        sqLiteDatabase.execSQL("UPDATE FRIEND SET ISBLOCK = 0 WHERE NAME = " + "'" + friend.getName() + "' ");
                        sqLiteDatabase.close();
                        UnBlockFriend unBlockFriend = new UnBlockFriend();
                        unBlockFriend.execute(my_id, user_id);
                        adapter.notifyDataSetChanged();
                    }
                }

            }


        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
