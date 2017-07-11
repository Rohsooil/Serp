package com.example.user.hanium;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements PageChatting.OnFragmentInteractionListener,
PageSenseMap.OnFragmentInteractionListener, PageMessage.OnFragmentInteractionListener,
        PageFriend.OnFragmentInteractionListener, PageETC.OnFragmentInteractionListener{
    TabLayout mTab;
    ViewPager viewPager;
    PagerAdapter mPagerAdapter;
    Fragment[] arrFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTab = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        arrFragments = new Fragment[5];
        arrFragments[0] = new PageSenseMap();
        arrFragments[1] = new PageFriend();
        arrFragments[2] = new PageMessage();
        arrFragments[3] = new PageChatting();
        arrFragments[4] = new PageETC();

        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), arrFragments);
        viewPager.setAdapter(mPagerAdapter);
        mTab.setupWithViewPager(viewPager);
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
                    return "Map";
                case 1:
                    return "친구";
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
    public void onFragmentInteraction(Uri uri){}
}
