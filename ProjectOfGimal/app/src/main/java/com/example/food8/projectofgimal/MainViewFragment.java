package com.example.food8.projectofgimal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MainViewFragment extends Fragment {
    ViewGroup v;
    Button mapBtn;
    Button foodBtn;
    Button boardBtn;
    MainActivity mainActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.main_view_fragment,container,false);
        mapBtn = (Button) v.findViewById(R.id.goToMap);
        foodBtn = (Button) v.findViewById(R.id.goFood);
        boardBtn = (Button) v.findViewById(R.id.goBoard);
        mainActivity = (MainActivity) getActivity();
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goToMap();
            }
        });
        boardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goBoard();
            }
        });
        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goFood();
            }
        });
        return v;
    }

}
