package com.example.user.bean;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PageSense.OnnListener  {
    TabLayout mTab;
    ViewPager viewPager;
    TextView state;
    PagerAdapter mPagerAdapter;
    Fragment[] arrFragments;
    Button logout_btn;
    String userEmail = "";
    UserInfo userInfo;
    String sss;
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alert_ex = new AlertDialog.Builder(this);
        alert_ex.setMessage("Sense Plus+ 을 종료하시겠습니까?");
        alert_ex.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        alert_ex.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        alert_ex.setTitle("확인");
        AlertDialog alert = alert_ex.create();
        alert.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userInfo = intent.getParcelableExtra("userInfo");
        userEmail = userInfo.getUserEmail();

        logout_btn = (Button) findViewById(R.id.logout_btn);
        mTab = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        state = (TextView) findViewById(R.id.state);
        arrFragments = new Fragment[5];
        arrFragments[0] = new PageFriend();
        arrFragments[1] = new PageSenseMap();
        arrFragments[2] = new PageSense();
        arrFragments[3] = new PageChatting();
        arrFragments[4] = new PageETC();
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(mPagerAdapter);
        mTab.setupWithViewPager(viewPager);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                state.setText(tab.getText());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void goDetailMessage(String value){
        Intent intent = new Intent(this,PageMessage_Detail1.class);
        intent.putExtra("value",value);
        startActivity(intent);
    }
    @Override
    public void Onn(String value) {
        sss = value;
        Log.d("ssss",sss);
    }

    public UserInfo getUserInfo(){
        return this.userInfo;
    }

    public void checkBlock(String my_id){
        Intent intent = new Intent(this,BlockedFriendActivity.class);
        intent.putExtra("my_id",my_id);
        startActivity(intent);
    }


    public void goPageSendSense(){
        Intent intent = new Intent(this,PageSendSense.class);
        startActivity(intent);
    }


    public class PagerAdapter extends FragmentPagerAdapter {
        private Fragment[] arrFragments;
        public PagerAdapter(FragmentManager fm, Fragment[] arrFragments){
            super(fm);
            this.arrFragments = arrFragments;
        }
        @Override
        public Fragment getItem(int position) {
            return arrFragments[position];
        }
        @Override
        public int getCount() {
            return arrFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "친구";
                case 1:
                    return "Map";
                case 2:
                    return "메세지";
                case 3:
                    return "채팅";
                case 4:
                    return "더 보기";
                default:
                    return "";
            }
        }
    }




}
