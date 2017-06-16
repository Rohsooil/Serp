package com.example.food8.projectofgimal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by food8 on 2017-06-16.
 */

public class Text_View_Fragment extends Fragment {
    ViewGroup v;
    TextView textView;
    String text;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.textview_fragment,container,false);
        textView = (TextView) v.findViewById(R.id.FoodViewer);
        text = getResources().getString(R.string.it_college);
        textView.setText(text);

        return v;
    }
}
