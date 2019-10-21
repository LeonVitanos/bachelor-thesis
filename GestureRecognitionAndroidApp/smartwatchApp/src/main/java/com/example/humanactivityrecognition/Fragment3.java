package com.example.humanactivityrecognition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {

    private Button restartButton;
    private TextView gestureText;
    private ImageView gestureImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment3_layout, container, false);

        gestureText = (TextView) view.findViewById(R.id.gestureText);
        gestureImg = (ImageView) view.findViewById(R.id.imageView);

        restartButton = (Button) view.findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).setViewPager(1);
                ((MainActivity)getActivity()).startSensor();
            }
        });

        return view;
    }

    public void wrongSpeed(String text){
        gestureText.setText("\n"+text+"\n");
        gestureImg.setVisibility(View.GONE);
    }

    public void showGesture(String text, int gesture){
        gestureText.setText(text);
        gestureImg.setVisibility(View.VISIBLE);
        int id = getResources().getIdentifier("gesture" + String.valueOf(gesture), "drawable", getActivity().getPackageName());
        gestureImg.setImageResource(id);
    }
}
