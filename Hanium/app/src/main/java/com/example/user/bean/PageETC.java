package com.example.user.bean;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PageETC extends Fragment {
    TextView info_name, info_nickname, info_email, info_id, info_birth, info_profile;
    Button btn_revise;
    ViewGroup v;
    private static String TAG = "allselectuser4";
    private static final String TAG_JSON="allselectuser";
    private static final String TAG_USER_EMAIL = "user_email";
    private static final String TAG_USER_BIRTH = "user_birth";
    private static final String TAG_USER_NAME = "user_name";
    private static final String TAG_USER_ID = "user_id";
    private static final String TAG_USER_NICKNAME = "user_nickname";
    private static final String TAG_USER_PROFILE = "user_profile";
    private static final String TAG_USER_PHONE = "user_phone";
    String user_id, user_email, user_password, user_phone, user_gender,
            user_birth, user_name, user_nickname, user_profile;
    EditText edit_nickname, edit_profile;
    String mJsonString;
    MainActivity mainActivity;
    UserInfo userInfo;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = (ViewGroup)inflater.inflate(R.layout.fragment_page_etc,container,false);
        info_name = (TextView) v.findViewById(R.id.info_name);
        info_nickname = (TextView) v.findViewById(R.id.info_nickname);
        info_email = (TextView) v.findViewById(R.id.info_email);
        info_id = (TextView) v.findViewById(R.id.info_id);
        info_birth = (TextView) v.findViewById(R.id.info_birth);
        info_profile = (TextView) v.findViewById(R.id.info_profile);
        btn_revise = (Button) v.findViewById(R.id.btn_revise);
        mainActivity = (MainActivity) getActivity();
        userInfo = mainActivity.getUserInfo();

        AllSelectUser4 allSelectUser4 = new AllSelectUser4();
        allSelectUser4.execute("http://211.253.30.146:7275/allselectuser.php");

        btn_revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = inflater.inflate(R.layout.revise, null);
                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("정보 수정");
                ad.setView(layout);
                edit_nickname = (EditText) layout.findViewById(R.id.edit_nickname);
                edit_profile = (EditText) layout.findViewById(R.id.edit_profile);
                ad.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        user_id = userInfo.getUserId().toString();
                        user_email = userInfo.getUserEmail().toString();
                        user_password = userInfo.getUserPw().toString();
                        user_gender = userInfo.getUserGender().toString();
                        user_name = userInfo.getUserName().toString();
                        user_birth = userInfo.getUserBirth().toString();
                        user_nickname = edit_nickname.getText().toString();
                        user_phone = userInfo.getUserPhone().toString();
                        user_profile = edit_profile.getText().toString();
                        ChangeUser task = new ChangeUser();
                        task.execute(user_id, user_email, user_password, user_phone, user_gender,
                                user_birth, user_name, user_nickname, user_profile);
                        info_nickname.setText(user_nickname);
                        info_profile.setText(user_profile);
                    }
                });
                ad.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        return v;
    }
    class ChangeUser extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getContext(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            String user_id = (String)params[0];
            String user_email = (String)params[1];
            String user_password = (String)params[2];
            String user_phone = (String)params[3];
            String user_gender = (String)params[4];
            String user_birth = (String)params[5];
            String user_name = (String)params[6];
            String user_nickname = (String)params[7];
            String user_profile = (String)params[8];

            String serverURL = "http://211.253.30.146:7275/changeuser.php";
            String postParameters = "user_id=" + user_id + "&user_email=" + user_email +
                    "&user_password=" + user_password + "&user_phone=" + user_phone + "&user_gender="
                    + user_gender + "&user_birth=" + user_birth + "&user_name=" + user_name +
                    "&user_nickname=" + user_nickname + "&user_profile=" + user_profile;

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
    private class AllSelectUser4 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(),
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
                    String user_id = item.getString(TAG_USER_ID);
                    String user_name = item.getString(TAG_USER_NAME);
                    String user_birth = item.getString(TAG_USER_BIRTH);
                    String user_email = item.getString(TAG_USER_EMAIL);
                    String user_phone = item.getString(TAG_USER_PHONE);
                    String user_nickname = item.getString(TAG_USER_NICKNAME);
                    String user_profile = item.getString(TAG_USER_PROFILE);
                    if(userInfo.getUserEmail().equals(user_email)){
                        info_name.setText(user_name);
                        info_nickname.setText(user_nickname);
                        info_email.setText(user_email);
                        info_birth.setText(user_birth);
                        info_id.setText(user_id);
                        info_profile.setText(user_profile);
                    }
                }
            } catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }
        }
    }
}
