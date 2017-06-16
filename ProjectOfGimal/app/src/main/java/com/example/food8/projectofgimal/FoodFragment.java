package com.example.food8.projectofgimal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by food8 on 2017-06-10.
 */

public class FoodFragment extends Fragment {

    ViewGroup v;
    Button chang, arm, vision;
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.food_fragment,container,false);
        mainActivity = (MainActivity) getActivity();
        chang = (Button) v.findViewById(R.id.chang_btn);
        vision = (Button) v.findViewById(R.id.vision_btn);
        arm = (Button) v.findViewById(R.id.arm_btn);

        chang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFood(1);
            }
        });

        arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFood(2);
            }
        });

        vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFood(3);
            }
        });

        return v;
    }
}
