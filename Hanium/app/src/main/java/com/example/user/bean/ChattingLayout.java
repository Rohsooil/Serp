package com.example.user.bean;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by food8 on 2017-07-19.
 */

public class ChattingLayout extends ConstraintLayout{
    Context mContext;
    LayoutInflater inflater;

    TextView nameText;
    TextView timeText;

    public ChattingLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }
    private void init(){
        inflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_chatting,this,true);
        nameText = (TextView) findViewById(R.id.chatter);
        timeText = (TextView)findViewById(R.id.sendChattime);
    }
    public void setChatSender(String sender){
        nameText.setText(sender);
    }
    public void setChatTime(String time){
        timeText.setText(time);
    }
}
