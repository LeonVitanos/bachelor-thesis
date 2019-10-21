package com.example.wearosapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {

    private Button stopButton;
    public TextView xText, yText, zText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);

        xText = (TextView)view.findViewById(R.id.xText);
        yText = (TextView)view.findViewById(R.id.yText);
        zText = (TextView)view.findViewById(R.id.zText);

        stopButton = (Button) view.findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    public void changeAxisText(float x_axis, float y_axis, float z_axis){
        Log.d("wgat", "2");
        xText.setText("X: " + x_axis);
        //yText.setText("Y: " + y_axis);
        //zText.setText("Z: " + z_axis);
    }
}
