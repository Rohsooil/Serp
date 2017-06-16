package com.example.food8.projectofgimal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;

/**
 * Created by food8 on 2017-06-02.
 */

public class SingInFragment extends Fragment {

    ViewGroup v;
    Button backBtn;
    com.google.android.gms.common.SignInButton signInButton;
    MainActivity mainActivity;
    CallbackManager callbackManager;
    LoginButton loginButton;
    EditText idText;
    EditText passwordText;
    Button mLogin;
    Button mSign;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.lonin_fragment,container,false);
        mainActivity = (MainActivity) getActivity();
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) v.findViewById(R.id.facebook_login_button);
        signInButton = (SignInButton) v.findViewById(R.id.signInButton);
        idText = (EditText) v.findViewById(R.id.IDtext);
        passwordText = (EditText) v.findViewById(R.id.password);
        mLogin = (Button) v.findViewById(R.id.logInBtn);
        backBtn = (Button) v.findViewById(R.id.goHome);
        mSign = (Button) v.findViewById(R.id.signBtn);

        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.gaipGoGo();
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idText.getText().toString();
                String pw = passwordText.getText().toString();
                mainActivity.myLogIn(id,pw);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mainActivity.backHome();
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.signInGoogle();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.facebookLogIn();
            }
        });
        return v;
    }
}
