package com.example.user.bean;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by user on 2017-08-14.
 */

public class SearchActivity extends AppCompatActivity {
    EditText search_name, search_birth, search_phone, search_name2, search_email;
    Button ok_idsearch, ok_pwsearch, back_btn2;
    private static String TAG = "allselectuser";
    private static final String TAG_JSON="allselectuser";
    private static final String TAG_USER_EMAIL = "user_email";
    private static final String TAG_USER_PW = "user_password";
    private static final String TAG_USER_BIRTH = "user_birth";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_USER_PHONE = "user_phone";
    private static int btn_type = 1;
    String mJsonString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_infosearch);
        search_name = (EditText) findViewById(R.id.search_name);
        search_birth = (EditText) findViewById(R.id.search_birth);
        search_phone = (EditText) findViewById(R.id.search_phone);
        search_name2 = (EditText) findViewById(R.id.search_name2);
        search_email = (EditText) findViewById(R.id.search_email);
        ok_idsearch = (Button) findViewById(R.id.btn_idsearch);
        ok_pwsearch = (Button) findViewById(R.id.btn_pwsearch);
        back_btn2 = (Button) findViewById(R.id.backbtn2);

        back_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok_idsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type = 1;
                AllSelectUser3 allSelectUser = new AllSelectUser3();
                allSelectUser.execute("http://211.253.30.146:7275/allselectuser.php");
            }
        });
        ok_pwsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_type = 2;
                AllSelectUser3 allSelectUser = new AllSelectUser3();
                allSelectUser.execute("http://211.253.30.146:7275/allselectuser.php");
            }
        });
    }

    private class AllSelectUser3 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SearchActivity.this,
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
                    String user_pw = item.getString(TAG_USER_PW);
                    String user_phone = item.getString(TAG_USER_PHONE);
                    String user_name = item.getString(TAG_USER_NAME);
                    String user_birth = item.getString(TAG_USER_BIRTH);
                    String user_email = item.getString(TAG_USER_EMAIL);
                    if(btn_type == 1 && search_name.getText().toString().equals(user_name) &&
                            search_birth.getText().toString().equals(user_birth)){
                        Toast.makeText(getApplicationContext(), "이메일 : " + user_email,
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(btn_type == 2 && search_email.getText().toString().equals(user_email) &&
                            search_name2.getText().toString().equals(user_name) &&
                            search_phone.getText().toString().equals(user_phone)){
                        Toast.makeText(getApplicationContext(), "비밀번호 : " + user_pw,
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "존재하지않는 정보입니다."
                , Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }
        }
    }
}
