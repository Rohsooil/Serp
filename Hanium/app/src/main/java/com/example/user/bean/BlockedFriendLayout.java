package com.example.user.bean;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by food8 on 2017-08-14.
 */

public class BlockedFriendLayout extends LinearLayout implements Checkable {
    Context mContext;
    LayoutInflater inflater;
    TextView blockedText;
    //CheckBox checkBox;

    public BlockedFriendLayout(Context context) {
        super(context);
        this.mContext = context;
        init();
    }


    private void init(){
        inflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_blocked_friend,this,true);
        blockedText = (TextView) findViewById(R.id.blockedName);
        //checkBox = (CheckBox) findViewById(R.id.isBlocked);
    }

    public void setName(String name){
        blockedText.setText(name);
    }

    @Override
    public void setChecked(boolean checked) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.isBlocked);
        if(checkBox.isChecked() != checked){
            checkBox.setChecked(checked);
        }
    }

    @Override
    public boolean isChecked() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.isBlocked);
        if(checkBox.isChecked()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void toggle() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.isBlocked);
        setChecked(checkBox.isChecked() ? false : true);
    }
}
