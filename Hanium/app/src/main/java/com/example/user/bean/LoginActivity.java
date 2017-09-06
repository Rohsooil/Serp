package com.example.user.bean;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by user on 2017-08-08.
 */

public class LoginActivity extends AppCompatActivity {
    EditText mailEditText, pwEditText;
    CheckBox rememberCheck;
    Button signupBtn, loginBtn, findBtn;
    Boolean rememberInfo;
    String loginEmail, loginPw, userName, userPhone, userBirth, userId, userGender;
    ArrayList<String> insertFriendList;

    private static String TAG = "allselectuser";
    private static final String TAG_USER_PW = "user_password";
    private static final String TAG_JSON="allselectuser";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_USER_EMAIL = "user_email";
    private static final String TAG_MY_ID = "my_id";
    private static final String TAG_USER_PHONE = "user_phone";
    private static final String TAG_USER_GENDER = "user_gender";
    private static final String TAG_USER_BIRTH = "user_birth";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_FRI_ID = "fri_id";
    private static final String TAG_FRI_BLOCK ="fri_block";
    private static final String TAG_CHAT_ID = "chat_id";
    private static final String TAG_SENSE_ID = "sense_id";
    private static final String TAG_GROUP_ID ="group_id";
    private static final String TAG_MESSAGE ="message";
    private static final String TAG_CHAT_TIME ="chat_time";
    private static final String TAG_CHAT_READ ="chat_read";
    private static final String TAG_GROUP_KEY = "group_key";
    private static final String TAG_PARTICIPANT_ID = "participant_id";
    private static final String TAG_LOCATION ="location";
    private static final String TAG_LONGITUDE ="longitude";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_QUESTION = "question";
    private static final String TAG_ANSWER = "answer";
    private static final String TAG_SENSE_TIME = "sensetime";
    private static final String TAG_USER_PROFILE = "user_profile";
    private static final String TAG_USER_NICKNAME = "user_nickname";


    FriendDB friendDB;
    SQLiteDatabase sqLiteDatabase, readDatabase, writeDatabase, messageDatabase;
    Handler handler;
    String mJsonString ="", mJsonString2 ="", mJsonString3 = "", mJsonString4 ="", mJsonString5 ="";
    String mail, pw;
    String my_id;
    UserInfo userInfo;
    String isblock = "0";
    DBHelper dbHelper;
    ArrayList<String> group_id_s, sense_id_s;
    HashSet hashSet1, hashSet2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        final SharedPreferences remember = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        rememberInfo = remember.getBoolean("rememberInfo", false);
        userId = remember.getString("inputId",null);
        userName = remember.getString("inputName",null);
        userBirth = remember.getString("inputBirth",null);
        userPhone = remember.getString("inputPhone",null);
        loginEmail = remember.getString("inputEmail",null);
        userGender = remember.getString("inputGender", null);
        loginPw = remember.getString("inputPw", null);
        mailEditText = (EditText) findViewById(R.id.mail_EditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        rememberCheck = (CheckBox) findViewById(R.id.rememberCheck);
        signupBtn = (Button) findViewById(R.id.signupButton);
        loginBtn = (Button) findViewById(R.id.loginButton);
        findBtn = (Button) findViewById(R.id.findButton);
        group_id_s = new ArrayList<String>();
        sense_id_s = new ArrayList<String>();
        hashSet1 = new HashSet();
        hashSet2 = new HashSet();

        insertFriendList = new ArrayList<String>();

        permissionCheck();

        if(loginEmail !=null && loginPw != null) {
            mailEditText.setText(loginEmail);
            pwEditText.setText(loginPw);
            userInfo = new UserInfo(userName,userId,loginEmail,loginPw,userPhone,userBirth, userGender);
            my_id = userInfo.getUserId();
            AllSelectFriend task = new AllSelectFriend();
            task.execute("http://211.253.30.146:7275/allselectfriend.php");
            AllSelectMessage task1 = new AllSelectMessage();
            task1.execute("http://211.253.30.146:7275/allselectmessage.php");
            final ProgressDialog mprogressDialog;
            mprogressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait", null, true, true);
            handler = new Handler(); //외부 뷰를 내부로 설정하기 위한 핸들러 객체 설정

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("userInfo", (Parcelable) userInfo);
                    rememberCheck.setChecked(true);
                    startActivity(intent);
                    mprogressDialog.dismiss();
                }
            }, 2000);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loginType = 0;
                mail = mailEditText.getText().toString();
                pw = pwEditText.getText().toString();
                if(mail.equals("") || pw.equals(""))
                    loginType = 1;

                switch (loginType){
                    case 1:
                        Toast.makeText(getApplicationContext(), "빈 항목이 있습니다.", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        AllSelectUser allSelectUser = new AllSelectUser();
                        allSelectUser.execute("http://211.253.30.146:7275/allselectuser.php");
                }
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void permissionCheck() {
        String[] permissions = {//import android.Manifest;
                Manifest.permission.ACCESS_FINE_LOCATION,   //GPS 이용권한
                Manifest.permission.ACCESS_COARSE_LOCATION, //네트워크/Wifi 이용 권한
        };

        //권한을 가지고 있는지 체크
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 승인 OK", Toast.LENGTH_LONG).show();// 권한 허가
                    // 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    Toast.makeText(this, "권한 거부", Toast.LENGTH_LONG).show();// 권한 거부
                    // 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class AllSelectUser extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this,
                    "Please Wait", null, true, true);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null) {
            } else {
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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();

                String line;

                while ((line = bufferedReader.readLine()) != null) {
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

        private void showResult() {
            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String user_mail = item.getString(TAG_USER_EMAIL);
                    String user_pw = item.getString(TAG_USER_PW);
                    String user_name = item.getString(TAG_USER_NAME);
                    String user_birth = item.getString(TAG_USER_BIRTH);
                    String user_Id = item.getString(TAG_USER_ID);
                    String user_phone = item.getString(TAG_USER_PHONE);
                    String user_gender = item.getString(TAG_USER_GENDER);

                    if(!mail.equals(user_mail)) {

                    }else{
                        if(pw.equals(user_pw)){
                            userInfo = new UserInfo(user_name,user_Id,user_mail,user_pw,user_phone,user_birth, user_gender);
                            my_id = userInfo.getUserId();
                            AllSelectFriend task = new AllSelectFriend();
                            task.execute("http://211.253.30.146:7275/allselectfriend.php");
                            AllSelectMessage task1 = new AllSelectMessage();
                            task1.execute("http://211.253.30.146:7275/allselectmessage.php");
                            final ProgressDialog mprogressDialog;
                            mprogressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait", null, true, true);
                            handler = new Handler(); //외부 뷰를 내부로 설정하기 위한 핸들러 객체 설정

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("userInfo", (Parcelable) userInfo);
                                    startActivity(intent);
                                    mprogressDialog.dismiss();
                                }
                            }, 2000);

                            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_LONG).show();
                            if(rememberCheck.isChecked()){
                                SharedPreferences remember = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor rememberLogin = remember.edit();
                                rememberLogin.putString("inputName", user_name);
                                rememberLogin.putString("inputId", user_Id);
                                rememberLogin.putString("inputEmail", user_mail);
                                rememberLogin.putString("inputPw", user_pw);
                                rememberLogin.putString("inputPhone", user_phone);
                                rememberLogin.putString("inputBirth", user_birth);
                                rememberLogin.putString("inputGender", user_gender);
                                rememberLogin.putBoolean("rememberinfo", true);
                                rememberLogin.commit();
                            }else{
                                SharedPreferences remember = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor rememberLogin = remember.edit();
                                rememberLogin.putString("inputName", null);
                                rememberLogin.putString("inputId", null);
                                rememberLogin.putString("inputEmail", null);
                                rememberLogin.putString("inputPw", null);
                                rememberLogin.putString("inputPhone", null);
                                rememberLogin.putString("inputBirth", null);
                                rememberLogin.putString("inputGender", null);
                                rememberLogin.putBoolean("rememberInfo", false);
                                rememberLogin.commit();
                            }
                            return;
                        }else{
                            Toast.makeText(getApplicationContext(), "비밀번호 다시 확인해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                Toast.makeText(getApplicationContext(), "존재하지않는 이메일입니다.", Toast.LENGTH_LONG).show();
                return;
            } catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }
        }
    }

    private class AllSelectFriend extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if ((result == null)||result.equals("")){
            }
            else {
                mJsonString2 = result;
                makeFriendList();
                if(mJsonString.equals("")||mJsonString==null){
                    FindUser findUser = new FindUser();
                    findUser.execute("http://211.253.30.146:7275/allselectuser.php");
                }else {
                    friendDbInsert();
                }
                mJsonString ="";
                mJsonString2="";
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

    //"http://211.253.30.146:7275/allselectmessage.php"
    private class AllSelectMessage extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            Log.d(TAG, "response  - " + result);

            if ((result == null)||result.equals("")){

            }
            else {
                mJsonString3 = result;
                insertMessage();
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

    //"http://211.253.30.146:7275/allselectkey.php"
    private class AllSelectKey extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if (result == null){
            }
            else {
                mJsonString5 = result;
                insertSelectKey();
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

    //"http://211.253.30.146:7275/allselectgroup.php"
    private class AllSelectGroup extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginActivity.this, "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response  - " + result);

            if ((result == null)){
            }
            else {
                mJsonString4 = result;
                insertGroup();
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

    private class FindUser extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(LoginActivity.this,"Please Wait", null, true, true);
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
                friendDbInsert();
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

    private void friendDbInsert(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String user_mail = item.getString(TAG_USER_EMAIL);
                String user_id = item.getString(TAG_USER_ID);
                String user_phone = item.getString(TAG_USER_PHONE);
                String user_name = item.getString(TAG_USER_NAME);
                String user_profile = item.getString(TAG_USER_PROFILE);
                String user_nickname = item.getString(TAG_USER_NICKNAME);
                for(String friendId : insertFriendList){
                    if(user_id.equals(friendId)){
                        int isAlreadyExist = 1;
                        friendDB = new FriendDB(getApplicationContext());
                        readDatabase = friendDB.getReadableDatabase();
                        Cursor cursor = readDatabase.rawQuery("SELECT * FROM FRIEND WHERE ISBLOCK = 0", null);
                        while(cursor.moveToNext()){
                            String innerMail = cursor.getString(1);
                            String innerId = cursor.getString(6);
                            if(innerMail.equals(user_mail)&&innerId.equals(my_id)){
                                isAlreadyExist = 0;
                            }
                        }
                        if(isAlreadyExist == 1) {
                            sqLiteDatabase = friendDB.getWritableDatabase();
                            sqLiteDatabase.execSQL("INSERT INTO FRIEND VALUES ( '" + user_name + "', " +
                                    "'" + user_mail + "', '" + user_phone + "', '" + user_profile +
                                    "', '" + user_nickname + "' , " + isblock + ", '" + my_id + "' )");
                            sqLiteDatabase.close();
                        }
                    }
                }
            }
        }catch (JSONException e){
            Log.d(TAG, "showResult : ", e);
        }
    }

    private void makeFriendList(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString2);
            JSONArray jsonArray = jsonObject.getJSONArray("allselectfriend");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String user_id = item.getString(TAG_MY_ID);
                String fri_id = item.getString(TAG_FRI_ID);
                isblock = item.getString(TAG_FRI_BLOCK);
                String fri_key = item.getString("fri_key");
                if (user_id.equals(my_id)) {
                    int already = 0;
                    insertFriendList.add(fri_id);
                    dbHelper = new DBHelper(getApplicationContext());
                    writeDatabase = dbHelper.getWritableDatabase();
                    readDatabase = dbHelper.getReadableDatabase();
                    Cursor cursor = readDatabase.rawQuery("SELECT * FROM friendinfo", null);
                    while (cursor.moveToNext()) {
                        String inner_key = cursor.getString(3);
                        if ((inner_key.equals(fri_key))||inner_key.equals("")||inner_key==null) {
                            already = 1;
                        }
                    }
                    if(already == 0){
                        writeDatabase = dbHelper.getWritableDatabase();
                        writeDatabase.execSQL("INSERT INTO friendinfo VALUES ( '" + user_id + "', '" + fri_id + "', '" + isblock + "', '" + fri_key + "' )");
                    }
                }
            }
        }catch (JSONException e){
            Log.d(TAG, "showResult : ", e);
        }
    }

    private void insertMessage(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString3);
            JSONArray jsonArray = jsonObject.getJSONArray("allselectmessage");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String sense_id = item.getString(TAG_SENSE_ID);
                String imy_id = item.getString(TAG_MY_ID);
                String group_id = item.getString(TAG_GROUP_ID);
                String message = item.getString(TAG_MESSAGE);
                String chat_time = item.getString(TAG_CHAT_TIME);
                String chat_read = item.getString(TAG_CHAT_READ);
                String chat_id = item.getString(TAG_CHAT_ID);
                dbHelper = new DBHelper(getApplicationContext());
                messageDatabase = dbHelper.getReadableDatabase();


                if(imy_id.equals(my_id)){
                    int already = 0;
                    Cursor cursor = messageDatabase.rawQuery("SELECT * FROM chattings",null);
                    while (cursor.moveToNext()) {
                        String inner_key = cursor.getString(6);
                        if ((inner_key.equals(chat_id))||inner_key.equals("")||inner_key == null) {
                            already = 1;
                        }
                    }
                    if(already == 0){
                        writeDatabase = dbHelper.getWritableDatabase();
                        writeDatabase.execSQL("INSERT INTO chattings VALUES ( '" + sense_id + "', '" + imy_id + "', '" + group_id + "', '" + message + "', '"
                                + chat_time + "', '" + chat_read + "', '" + chat_id + "' )");
                        group_id_s.add(group_id);
                        hashSet1.addAll(group_id_s);
                        group_id_s.clear();
                        group_id_s.addAll(hashSet1);
                        sense_id_s.add(sense_id);
                        hashSet2.addAll(sense_id_s);
                        sense_id_s.clear();
                        sense_id_s.addAll(hashSet2);
                        AllSelectGroup allSelectGroup = new AllSelectGroup();
                        allSelectGroup.execute("http://211.253.30.146:7275/allselectgroup.php");
                        AllSelectKey allSelectKey = new AllSelectKey();
                        allSelectKey.execute("http://211.253.30.146:7275/allselectkey.php");
                    }
                }
            }
        }catch (JSONException e){
            Log.d(TAG, "showResult : ", e);
        }
    }

    private void insertGroup(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString4);
            JSONArray jsonArray = jsonObject.getJSONArray("allselectgroup");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String group_id = item.getString(TAG_GROUP_ID);
                String group_key = item.getString(TAG_GROUP_KEY);
                String participant_id = item.getString(TAG_PARTICIPANT_ID);
                dbHelper = new DBHelper(getApplicationContext());
                readDatabase = dbHelper.getReadableDatabase();
                for(String mgroup_id : group_id_s) {
                    if (group_id.equals(mgroup_id)) {
                        int already = 0;
                        Cursor cursor = readDatabase.rawQuery("SELECT * FROM chatgroup", null);
                        while (cursor.moveToNext()) {
                            String inner_Key = cursor.getString(2);
                            if ((inner_Key.equals(group_key) || inner_Key.equals("") || inner_Key == null)) {
                                already = 1;
                            }
                        }
                        if (already == 0) {
                            writeDatabase = dbHelper.getWritableDatabase();
                            writeDatabase.execSQL("INSERT INTO chatgroup VALUES ( '" + group_id + "', '" + participant_id + "', '" + group_key + "' )");
                        }
                    }
                }
            }
        }catch (JSONException e){
            Log.d(TAG, "showResult : ", e);
        }
    }

    private void insertSelectKey(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString5);
            JSONArray jsonArray = jsonObject.getJSONArray("allselectkey");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String sense_id = item.getString(TAG_SENSE_ID);
                String location = item.getString(TAG_LOCATION);
                String latitude = item.getString(TAG_LATITUDE);
                String longitude = item.getString(TAG_LONGITUDE);
                String question = item.getString(TAG_QUESTION);
                String answer = item.getString(TAG_ANSWER);
                String sensetime = item.getString(TAG_SENSE_TIME);
                dbHelper = new DBHelper(getApplicationContext());
                readDatabase = dbHelper.getReadableDatabase();
                for(String msense_id : sense_id_s) {
                    if (sense_id.equals(msense_id)) {

                        int already = 0;
                        Cursor cursor = readDatabase.rawQuery("SELECT * FROM sensekey", null);
                        while (cursor.moveToNext()) {
                            String inner_Key = cursor.getString(0);
                            if (inner_Key.equals(sense_id) || inner_Key.equals("") || inner_Key == null) {
                                already = 1;
                            }
                        }
                        if (already == 0) {
                            writeDatabase = dbHelper.getWritableDatabase();
                            writeDatabase.execSQL("INSERT INTO sensekey VALUES ( '" + sense_id + "', '" + location + "', '" + latitude +
                                    "', '" + longitude + "', '" + question + "', '" + answer + "', '" + sensetime + "' )");
                        }
                    }
                }
            }
        }catch (JSONException e){
            Log.d(TAG, "showResult : ", e);
        }
    }


}
