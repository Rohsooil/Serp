package com.example.food8.projectofgimal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by food8 on 2017-06-12.
 */

public class Food_change_Fragment extends Fragment {
    ViewGroup v;
    Food_info vison_food_info, arm_food_info, chang_food_info;
    TextView gyojikone, ilpoom, hansick;
    int gubun;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.food_info_fragment,container,false);
        gyojikone = (TextView)v.findViewById(R.id.gyojikone);
        ilpoom = (TextView)v.findViewById(R.id.ilpooom);
        hansick = (TextView)v.findViewById(R.id.hansick);
        gubun = getArguments().getInt("gubun");
        chang_food_info = (Food_info) getArguments().get("chang");
        arm_food_info = (Food_info) getArguments().get("arm");
        vison_food_info = (Food_info) getArguments().get("vision");

        if(gubun == 1) {
            chang_food_info.setGyojikone(RemoveHTMLTag(chang_food_info.getGyojikone()).replaceAll("amp;",""));
            chang_food_info.setSpepoom(RemoveHTMLTag(chang_food_info.getSpepoom()).replaceAll("amp;",""));
            chang_food_info.setIlpoom(RemoveHTMLTag(chang_food_info.getIlpoom()).replaceAll("amp;",""));
            gyojikone.setText(chang_food_info.getGyojikone());
            ilpoom.setText(chang_food_info.getIlpoom());
            hansick.setText(chang_food_info.getSpepoom());
        }else if(gubun == 2){
            arm_food_info.setGyojikone(RemoveHTMLTag(arm_food_info.getGyojikone()).replaceAll("amp;",""));
            arm_food_info.setIlpoom(RemoveHTMLTag(arm_food_info.getIlpoom()).replaceAll("amp;",""));
            arm_food_info.setSpepoom(RemoveHTMLTag(arm_food_info.getSpepoom()).replaceAll("amp;",""));
            gyojikone.setText(arm_food_info.getGyojikone().replaceAll("amp;",""));
            ilpoom.setText(arm_food_info.getIlpoom());
            hansick.setText(arm_food_info.getSpepoom());
        }else if (gubun ==3){
            vison_food_info.setGyojikone(RemoveHTMLTag(vison_food_info.getGyojikone()).replaceAll("amp;",""));
            vison_food_info.setSpepoom(RemoveHTMLTag(vison_food_info.getSpepoom()).replaceAll("amp;",""));
            vison_food_info.setIlpoom(RemoveHTMLTag(vison_food_info.getIlpoom()).replaceAll("amp;",""));
            gyojikone.setText(vison_food_info.getGyojikone());
            ilpoom.setText(vison_food_info.getIlpoom());
            hansick.setText(vison_food_info.getSpepoom());
        }

        return v;
    }

    public String RemoveHTMLTag(String changeStr){
        if(changeStr != null && !changeStr.equals("")){
            changeStr = changeStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "\n");
        }else{
            changeStr = "";
        }
        changeStr = changeStr.replaceAll("\\</.*?\\>", "");
        return changeStr;
    }

}
