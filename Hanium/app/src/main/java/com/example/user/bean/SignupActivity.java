package com.example.user.bean;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

import static com.example.user.bean.R.id.check_id;

public class SignupActivity extends AppCompatActivity {
    private static String TAG = "insertuser_Main";
    EditText id_txt, name_txt, email_txt, pw_txt, pw2_txt, birth_txt, phone_txt;
    RadioButton mail_btn, femail_btn;
    CheckBox check_signup;
    Button ok_signup, back_btn, check_mail;
    ProgressDialog progressDialog;
    String check_num = "0";
    String mJsonString;
    private static final String TAG_JSON = "allselectuser";
    private static final String TAG_USER_EMAIL = "user_email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup);
        name_txt = (EditText) findViewById(R.id.name_txt);
        email_txt = (EditText) findViewById(R.id.email_txt);
        pw_txt = (EditText) findViewById(R.id.pw_txt);
        pw2_txt = (EditText) findViewById(R.id.pw2_txt);
        birth_txt = (EditText) findViewById(R.id.birth_txt);
        phone_txt = (EditText) findViewById(R.id.phone_txt);
        mail_btn = (RadioButton) findViewById(R.id.mail_radio);
        femail_btn = (RadioButton) findViewById(R.id.femail_radio);
        check_signup = (CheckBox) findViewById(R.id.check_signup);
        ok_signup = (Button) findViewById(R.id.ok_signup);
        back_btn = (Button) findViewById(R.id.backbtn);
        check_mail = (Button) findViewById(check_id);


        check_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllSelectUser2 allSelectUser = new AllSelectUser2();
                allSelectUser.execute("http://211.253.30.146:7275/allselectuser.php");
            }
        });

        ok_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check_signup.isChecked()){
                    Toast.makeText(getApplicationContext(), "동의를 눌러주십시오.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String user_name = name_txt.getText().toString();
                String user_email = email_txt.getText().toString();
                String user_pw = pw_txt.getText().toString();
                String user_pw2 = pw2_txt.getText().toString();
                String user_phone = phone_txt.getText().toString();
                String user_birth = birth_txt.getText().toString();
                String user_gender = "";
                if(mail_btn.isChecked())
                    user_gender = "1";
                else if(femail_btn.isChecked())
                    user_gender = "0";
                if(!user_pw.equals(user_pw2)){
                    Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주십시오.",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if(check_num.equals("0")){
                    Toast.makeText(getApplicationContext(), "아이디 중복을 확인해주십시오",
                            Toast.LENGTH_LONG).show();
                    return;
                }else {
                    InsertUser task = new InsertUser();
                    task.execute(user_email, user_pw, user_phone, user_gender, user_birth,
                            user_name);
                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_LONG).show();
                    finish();
                }
        }
     });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class InsertUser extends AsyncTask<String, Void, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressDialog = ProgressDialog.show(SignupActivity.this, "Please Wail", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {
            String user_email = params[0];
            String user_pw = params[1];
            String user_phone = params[2];
            String user_gender = params[3];
            String user_birth = params[4];
            String user_name = params[5];

            String serverURL = "http://211.253.30.146:7275/insertuser.php";
            String postParameters = "&user_email=" + user_email +
                    "&user_password=" + user_pw + "&user_phone=" + user_phone +
                    "&user_gender=" + user_gender + "&user_birth=" + user_birth + "&user_name=" +
                    user_name;
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
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
    private class AllSelectUser2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SignupActivity.this,
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
                    if(email_txt.getText().toString().equals(user_mail)) {
                        Toast.makeText(getApplicationContext(), "중복된 이메일 입니다.",
                                Toast.LENGTH_LONG).show();
                        check_num = "0";
                        return;
                    }
                }
                    Toast.makeText(getApplicationContext(), "사용가능한 이메일 입니다.",
                            Toast.LENGTH_LONG).show();
                    check_num = "1";
            } catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }
        }
    }
}

