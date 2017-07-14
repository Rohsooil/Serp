package com.example.user.hanium;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by food8 on 2017-07-12.
 */

public class FriendLayout extends LinearLayout {
    Context mContext;
    LayoutInflater inflater;

    TextView nameText;

    public FriendLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }
    private void init(){
        inflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_friend,this,true);
        nameText = (TextView) findViewById(R.id.friendName);
    }

    public void setName(String name){
        nameText.setText(name);
    }
}
