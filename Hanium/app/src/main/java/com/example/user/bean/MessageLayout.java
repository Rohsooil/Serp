package com.example.user.bean;

import android.app.Activity;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by TaeYoung on 2017-08-25.
 */

public class MessageLayout extends ConstraintLayout {
    Context mContext;
    LayoutInflater inflater;
    TextView contentText;
    TextView nameText;
    TextView timeText;

    public MessageLayout(Context context) {
        super(context);
        mContext = context;
        init();
    }
    private void init(){
        inflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_message,this,true);
        nameText = (TextView) findViewById(R.id.sender);
        timeText = (TextView)findViewById(R.id.sendtime);
        contentText = (TextView) findViewById(R.id.sensecontent);
    }
    public void setSender(String sender){
        nameText.setText(sender);
    }
    public void setTime(String time){
        timeText.setText(time);
    }
    public void setContentText(String contenttext) {
        contentText.setText(contenttext);
    }
}
