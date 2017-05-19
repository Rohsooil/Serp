package com.example.food8.hooooooomework;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerLayout extends LinearLayout {
    Context mContext;
    LayoutInflater inflater;

    ImageView imageView;
    TextView nameTextView;
    TextView companyTextView;
    TextView songTextView;


    public SingerLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SingerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        // 아이템의 화면을 구성한 XML 레이아웃(singer_item.xml)을 인플레이션

        inflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item,this,true);

        //부분화면 레이아웃에 정의된 객체 참조
        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        companyTextView = (TextView) findViewById(R.id.companyTextView);
        songTextView = (TextView) findViewById(R.id.songTextView);
    }

    public void setImage(int resId) {//이미지 리소스 id 설정
        imageView.setImageResource(resId);
    }

    public void setNameText(String name) {//이름 설정
        nameTextView.setText(name);
    }

    public void setCompany(String company) {//소속 설정
        companyTextView.setText(company);
    }

    public void setSong(String song) {//노래 설정
        songTextView.setText(song);
    }
}