package com.example.user.bean;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
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


public class PageFriend extends Fragment {
    private static String TAG = "allselectuser";

    private static final String TAG_JSON="allselectuser";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_USER_EMAIL = "user_email";
    private static final String TAG_MY_ID = "my_id";
    private static final String TAG_USER_PASSWORD = "user_password";
    private static final String TAG_USER_PHONE = "user_phone";
    private static final String TAG_USER_GENDER = "user_gender";
    private static final String TAG_USER_BIRTH = "user_birth";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_FRI_ID = "fri_id";
    private static final String TAG_FRI_BLOCK ="fri_block";
    private static final String TAG_USER_NICKNAME = "user_nickname";
    private static final String TAG_USER_PROFILE = "user_profile";

    String func = "";
    Handler handler;
    String mJsonString;
    String mJsonString2;
    String names = "", mailOrPhone = "", my_id ="";
    String profile = "";
    String nickname = "";
    ViewGroup v;
    ListView listView;
    ArrayList<Friend> friendList;
    ArrayList<String> insertFriendList;
    MyAdapter adapter;
    EditText searchEditText;
    FloatingActionButton fab,fab1,fab2,fab3;
    boolean isFABOpen;
    FriendDB friendDB;
    SQLiteDatabase sqLiteDatabase, readSQL;
    AlertDialog alertDialog;
    MainActivity mainActivity;
    Friend friendDelete;
    String fri_id ="";
    Friend blockedFriend;
    String userEmail = "";
    UserInfo userInfo;
    String friendMail ="", friendPhone ="";
    //EditText nameText;
    EditText mailText;
    DBHelper dbHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("OnCreate","OnCreate 호출");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.e("OnStart","OnStart 호출");
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("OnResume","OnResume 호출");
        //친구목록 만들고 디비 불러옴
        friendList = new ArrayList<Friend>();
        friendDB = new FriendDB(getContext());
        sqLiteDatabase = friendDB.getReadableDatabase();
        //쿼리문으로 FRIEND 테이블을 불러온다.
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM FRIEND WHERE ISBLOCK = 0", null);
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
                friendList.add(new Friend(friendName, friendMail, friendPhone, friendProfile, friendNickname, 0));
            }
        }
        adapter = new MyAdapter(getContext(),R.layout.list_friend,friendList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e("OnActivityCreated","OnActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = (ViewGroup)inflater.inflate(R.layout.fragment_page_friend,container,false);
        Log.e("OnCreateView","OnCreateView 호출");
        searchEditText =  (EditText) v.findViewById(R.id.searchEdit);
        //플로팅 버튼
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab1 = (FloatingActionButton) v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) v.findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) v.findViewById(R.id.fab3);
        mainActivity = (MainActivity) getActivity();
        userInfo = mainActivity.getUserInfo();
        userEmail = userInfo.getUserEmail();
        my_id = userInfo.getUserId();

        dbHelper = new DBHelper(getContext());
        readSQL = dbHelper.getReadableDatabase();
        Cursor cursor1 = readSQL.rawQuery("SELECT * FROM sensekey",null);
        while(cursor1.moveToNext()){
            String my_iid = cursor1.getString(0);
            String fri_iid = cursor1.getString(1);
            String isblocking = cursor1.getString(2);
            //String fri_key = cursor1.getString(3);
            Toast.makeText(getContext(),my_iid + " " + fri_iid + " " + isblocking + " ",Toast.LENGTH_LONG ).show();
        }


        //친구목록 만들고 디비 불러옴
        friendList = new ArrayList<Friend>();
        friendDB = new FriendDB(getContext());
        sqLiteDatabase = friendDB.getReadableDatabase();
        //쿼리문으로 FRIEND 테이블을 불러온다.
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM FRIEND WHERE ISBLOCK = 0", null);
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

            if(!isCurrentUser) {
                friendList.add(new Friend(friendName, friendMail, friendPhone, friendProfile, friendNickname, 0));
            }
        }
        //리스트뷰를 만들고 어뎁터에 추가해준다.
        listView = (ListView) v.findViewById(R.id.friendList);
        adapter = new MyAdapter(getContext(),R.layout.list_friend,friendList);
        listView.setAdapter(adapter);

        //리스트뷰 내용 클릭 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                final Friend friend = (Friend) adapter.getItem(position);
                final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {  //삭제버튼
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog = dialog.create();
                alertDialog.setTitle(friend.getName());
                final LinearLayout dialogLayout = new LinearLayout(getContext());
                dialogLayout.setOrientation(LinearLayout.VERTICAL);
                final Button deleteBtn = new Button(getActivity());         //이하 삭제버튼
                final Button blockBtn = new Button(getActivity());
                final Button sendMsgBtn = new Button(getActivity());
                final TextView mailView = new TextView(getActivity());
                mailView.setText(friend.getMail());
                mailView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                sendMsgBtn.setText("센스 전송!");
                sendMsgBtn.setTextColor(Color.BLUE);
                deleteBtn.setTextColor(Color.YELLOW);
                deleteBtn.setText("삭제");
                blockBtn.setTextColor(Color.RED);
                blockBtn.setText("차단");
                dialogLayout.addView(mailView);
                dialogLayout.addView(sendMsgBtn);
                dialogLayout.addView(blockBtn);
                dialogLayout.addView(deleteBtn);
                alertDialog.setView(dialogLayout);
                blockBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        func = "block";
                        blockedFriend = friend;
                        AllSelectUser task = new AllSelectUser();
                        task.execute("http://211.253.30.146:7275/allselectuser.php");

                        alertDialog.dismiss();
                    }
                });
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        func = "delete";
                        friendDelete = friend;
                        Toast.makeText(getContext(),friendDelete.getMail(),Toast.LENGTH_LONG).show();
                        AllSelectUser task = new AllSelectUser();
                        task.execute("http://211.253.30.146:7275/allselectuser.php");

                        alertDialog.dismiss();
                    }
                });
                sendMsgBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(),"dd",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity(),PageSendSense.class);
                        startActivity(intent);
                        //  mainActivity = (MainActivity) getActivity();
                        //  mainActivity.goPageSendSense();

                    }
                });
                alertDialog.show();

                Toast.makeText(getContext(),friend.getName(),Toast.LENGTH_LONG).show();
            }
        });


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String filterText = s.toString();
                ((MyAdapter)listView.getAdapter()).getFilter().filter(filterText);
            }
        });

        //플로팅버튼 클릭이벤트
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),userEmail,Toast.LENGTH_LONG).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity = (MainActivity) getActivity();
                mainActivity.checkBlock(my_id);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(isFABOpen){
                    AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                    ad.setTitle("친구 추가");

                    final LinearLayout dialogLayout = new LinearLayout(getContext());
                    dialogLayout.setOrientation(LinearLayout.VERTICAL);

                    mailText = new EditText(getActivity());
                    mailText.setHint("번호나 이메일을 입력하세요");

                    dialogLayout.addView(mailText);
                    ad.setView(dialogLayout);

                    ad.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(mailText.getText().toString().equals("")||mailText.getText().toString()==null){
                                Toast.makeText(getContext(),"이름과 번호를 입력해주세요.",Toast.LENGTH_LONG).show();
                            }else {
                                func = "add";
                                AllSelectUser task = new AllSelectUser();
                                task.execute("http://211.253.30.146:7275/allselectuser.php");
                                /////////
                                dialog.dismiss();
                            }
                        }
                    });

                    ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                }
            }
        });
        v.setFocusableInTouchMode(true);
        v.requestFocus();

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK){
                    if(isFABOpen){
                        closeFABMenu();
                    }else{
                        ((MainActivity)getActivity()).onBackPressed();
                    }
                    return true;
                }else {
                    return false;
                }
            }
        });

        return v;
    }

    //플로팅 메뉴 보여주는 메소드
    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_130));
        fab3.animate().translationY(-getResources().getDimension(R.dimen.standard_195));
    }
    //플로팅 메뉴 닫아주는 메소드
    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        fab3.animate().translationY(0);
    }

    private class AllSelectUser extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){
                Toast.makeText(getContext(),"asdf",Toast.LENGTH_LONG).show();
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

    private class InsertFriend extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String my_id = (String)params[0];
            String fri_id = (String)params[1];
            String serverURL = "http://211.253.30.146:7275/insertfriend.php";
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

    private class BlockFriend extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String my_id = (String) params[0];
            String fri_id = (String) params[1];

            String serverURL = "http://211.253.30.146:7275/blockfriend.php";
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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = bufferedReader.readLine()) != null) {
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

    private class DeleteFriend extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }

        @Override
        protected String doInBackground(String... params) {
            String my_id = (String)params[0];
            String fri_id = (String)params[1];

            String serverURL = "http://211.253.30.146:7275/deletefriend.php";
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

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            if(func.equals("add")) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);
                    String user_mail = item.getString(TAG_USER_EMAIL);
                    String user_id = item.getString(TAG_USER_ID);
                    String user_phone = item.getString(TAG_USER_PHONE);
                    String user_name = item.getString(TAG_USER_NAME);
                    String user_profile = item.getString(TAG_USER_PROFILE);
                    String user_nickname = item.getString(TAG_USER_NICKNAME);

                    names = user_name;
                    profile = user_profile;
                    nickname = user_nickname;
                    mailOrPhone = mailText.getText().toString();

                    if(mailOrPhone.contains("010")){
                        friendPhone = mailOrPhone.substring(3);
                    }else if(mailOrPhone.contains("@")){
                        friendMail = mailOrPhone;
                    }

                    if ((user_phone.equals(friendPhone)||user_mail.equals(friendMail)) && !(user_id.equals(fri_id))) {
                        fri_id = user_id;
                        Toast.makeText(getContext(), "친구 번호 일치 ㅇㅋ" + user_id, Toast.LENGTH_LONG).show();
                        int a = 0;
                        //names = nameText.getText().toString();
                        friendDB = new FriendDB(getContext());
                        sqLiteDatabase = friendDB.getWritableDatabase();

                        for (Friend friend : friendList) {
                            if (friend.getMail().equals(user_mail)|| friend.getPhone().equals(user_phone)) {
                                a = 1;
                                break;
                            }
                        }
                        if (a == 0) {
                            sqLiteDatabase.execSQL("INSERT INTO FRIEND VALUES ( '" + names + "', '" +
                                    friendMail + "', '" + friendPhone + "', '" + profile + "', '" +
                                    nickname + "', " +  "0" + ", '" + my_id +"' " + " )");
                            friendList.add(new Friend(names, friendMail, friendPhone, profile, nickname, 0));

                            InsertFriend task2 = new InsertFriend();
                            task2.execute(my_id, user_id);

                            //Toast.makeText(getContext(), names + " 추가" + user_id, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "이미 저장되어 있는 친구입니다.", Toast.LENGTH_LONG).show();
                        }
                        fri_id ="";
                        friendMail ="";
                        friendPhone ="";
                        sqLiteDatabase.close();
                        adapter.notifyDataSetChanged();
                    } else if (user_id.equals(fri_id)) {
                        //Toast.makeText(getContext(), "이미 저장되어 있는 사용자입니다." + fri_id, Toast.LENGTH_LONG).show();
                    } else if (!user_mail.equals(mailOrPhone)) {
                        //Toast.makeText(getContext(), "일치하는 번호나 이메일이 없습니다.", Toast.LENGTH_LONG).show();
                    }

                }
            } else if(func.equals("block")){
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);
                    String user_mail = item.getString(TAG_USER_EMAIL);
                    String user_id = item.getString(TAG_USER_ID);
                    if(blockedFriend.getMail().equals(user_mail)) {

                        BlockFriend blockFriend = new BlockFriend();
                        blockFriend.execute(my_id, user_id);
                        friendDB = new FriendDB(getContext());
                        sqLiteDatabase = friendDB.getWritableDatabase();
                        sqLiteDatabase.execSQL("UPDATE FRIEND SET ISBLOCK = 1 WHERE NAME = " + "'" + blockedFriend.getName() + "' ");
                        sqLiteDatabase.close();
                        friendList.remove(blockedFriend);
                        adapter.notifyDataSetChanged();
                    }

                }
            } else if(func.equals("delete")){
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);
                    String user_mail = item.getString(TAG_USER_EMAIL);
                    String user_id = item.getString(TAG_USER_ID);
                    if((!(friendDelete==null))&&friendDelete.getMail().equals(user_mail)) {
                        DeleteFriend deleteFriend = new DeleteFriend();
                        deleteFriend.execute(my_id, user_id);
                        friendDB = new FriendDB(getContext());
                        sqLiteDatabase = friendDB.getWritableDatabase();
                        sqLiteDatabase.execSQL("DELETE FROM FRIEND WHERE NAME = '" + friendDelete.getName() + "';");
                        friendList.remove(friendDelete);
                        friendDelete = null;
                        adapter.notifyDataSetChanged();

                        sqLiteDatabase.close();
                    }
                }
            }
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

    //정렬이 가능한 어뎁터 클래스 구현
    class MyAdapter extends BaseAdapter implements Filterable {
        Context mcontext;
        int list_friend;
        ArrayList<Friend> afriendList = new ArrayList<Friend>();
        ArrayList<Friend> filterdFriendList;
        LayoutInflater layoutInflater;
        Filter listFilter;

        public MyAdapter(Context context, int list_friend, ArrayList<Friend> friendList){
            mcontext = context;
            this.list_friend = list_friend;
            this.afriendList = friendList;
            filterdFriendList = afriendList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return filterdFriendList.size();
        }

        @Override
        public Object getItem(int position) {
            return filterdFriendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            FriendLayout friendLayout = null;
            if(convertView == null){
                friendLayout = new FriendLayout(mcontext);
            } else {
                friendLayout = (FriendLayout)convertView;
            }

            Friend friend = filterdFriendList.get(position);
            friendLayout.setName(friend.getNickname());
            friendLayout.setProfile(friend.getProfile());
            return friendLayout;
        }

        @Override
        public Filter getFilter() {
            if(listFilter == null){
                listFilter = new ListFilter();
            }
            return listFilter;
        }

        private class ListFilter extends Filter{

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if(constraint == null||constraint.length() ==0) {
                    results.values = afriendList;
                    results.count = afriendList.size();
                }else{
                    ArrayList<Friend> friends = new ArrayList<Friend>();
                    for(Friend friend : afriendList){
                        if(friend.getName().toUpperCase().contains(constraint.toString().toUpperCase())){
                            friends.add(friend);
                        }
                    }
                    results.values = friends;
                    results.count = friends.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterdFriendList = (ArrayList<Friend>) results.values;

                if(results.count > 0){
                    notifyDataSetChanged();
                } else{
                    notifyDataSetInvalidated();
                }
            }
        }
    }

}
