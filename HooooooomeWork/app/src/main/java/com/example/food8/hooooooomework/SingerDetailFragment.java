package com.example.food8.hooooooomework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by food8 on 2017-05-16.
 */

public class SingerDetailFragment extends Fragment {
    ViewGroup v;
    SingerItem item;
    ImageView imageView;
    TextView nameTextView;
    TextView companyTextView;
    TextView songTextView;
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.singer_detail,container,false);
        item = (SingerItem) getArguments().get("data");

        imageView = (ImageView) v.findViewById(R.id.imageView);
        nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        companyTextView = (TextView) v.findViewById(R.id.companyTextView);
        songTextView = (TextView) v.findViewById(R.id.songTextView);
        button = (Button) v.findViewById(R.id.backBtn);

        imageView.setImageResource(item.getResId());
        nameTextView.setText(item.getName());
        companyTextView.setText(item.getCompany());
        songTextView.setText(item.getSong());

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.backToList();
            }
        });

        return v;
    }
}
