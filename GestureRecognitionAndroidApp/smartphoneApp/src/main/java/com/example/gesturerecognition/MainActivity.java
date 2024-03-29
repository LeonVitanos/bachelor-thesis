package com.example.gesturerecognition;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.tensorflow.lite.Interpreter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import android.content.res.AssetFileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xText, yText, zText, textView;
    private Sensor mySensor;
    private SensorManager SM;
    private Boolean recordingXYZ=false;
    Interpreter tflite;
    int count = 0;
    ArrayList<Float> x = new ArrayList<Float>();
    ArrayList<Float> y = new ArrayList<Float>();
    ArrayList<Float> z = new ArrayList<Float>();

    long last_time_stamp = 0;
    long last_time_stamp_added = 0;
    float temp_x = 0;
    float temp_y = 0;
    float temp_z = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load Model
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (Exception ex){
            System.out.println("2");
        }

        // Create Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        // Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Assign TextView
        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);

        final Button button = findViewById(R.id.mainButton);
        final TextView gestureText = findViewById(R.id.gestureText);
        final ImageView gestureImage = findViewById(R.id.imageView);
        final TextView instructions = findViewById(R.id.instructions);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(recordingXYZ) {
                    SM.unregisterListener(MainActivity.this);
                    button.setText("Start");
                    recordingXYZ = false;
                    last_time_stamp_added=0;

                    xText.setVisibility(View.INVISIBLE);
                    yText.setVisibility(View.INVISIBLE);
                    zText.setVisibility(View.INVISIBLE);
                    gestureText.setVisibility(View.VISIBLE);

                    if(count<8 || count>30){
                        if(count<8) gestureText.setText("Too fast, try slower");
                        else if(count>30) gestureText.setText("Too slow, try faster");
                        count=0;
                        return;
                    }

                    ArrayList<Float> x_haar = new ArrayList<Float>();
                    ArrayList<Float> y_haar = new ArrayList<Float>();
                    ArrayList<Float> z_haar = new ArrayList<Float>();
                    ArrayList<Float> xyz_haar = new ArrayList<Float>();

                    x_haar = multilevel_haar_transform(x);
                    y_haar = multilevel_haar_transform(y);
                    z_haar = multilevel_haar_transform(z);

                    while(x_haar.size()<8)
                        x_haar.add((float)0);

                    while(y_haar.size()<8)
                        y_haar.add((float)0);

                    while(z_haar.size()<8)
                        z_haar.add((float)0);

                    for (int counter = 0; counter < 8; counter++)
                        xyz_haar.add(x_haar.get(counter));

                    for (int counter = 0; counter < 8; counter++)
                        xyz_haar.add(y_haar.get(counter));

                    for (int counter = 0; counter < 8; counter++)
                        xyz_haar.add(z_haar.get(counter));

                    float[][] gesture = doInference(xyz_haar);
                    float max_percentage=0;
                    int gesture_class=0;
                    for(int l=0; l<20; l++) {
                        if (gesture[0][l] > max_percentage) {
                            max_percentage = gesture[0][l];
                            gesture_class=l;
                        }
                    }

                    x.clear();
                    y.clear();
                    z.clear();

                    gestureText.setText(String.format("%.2f", max_percentage*100) + "% sure it is gesture:");
                    System.out.println(gesture_class+1);
                    int id = getResources().getIdentifier("gesture" + String.valueOf(gesture_class+1), "drawable", getPackageName());
                    gestureImage.setImageResource(id);
                    count=0;
                    gestureImage.setVisibility(View.VISIBLE);
                }
                else {
                    SM.registerListener(MainActivity.this, mySensor, SensorManager.SENSOR_DELAY_GAME);
                    button.setText("Stop");
                    recordingXYZ = true;
                    instructions.setVisibility(View.INVISIBLE);

                    xText.setVisibility(View.VISIBLE);
                    yText.setVisibility(View.VISIBLE);
                    zText.setVisibility(View.VISIBLE);

                    gestureText.setVisibility(View.INVISIBLE);
                    gestureImage.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public float[][] doInference(ArrayList<Float> xyz_arrlist){
        float[] ret = new float[xyz_arrlist.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = xyz_arrlist.get(i).intValue();
        }
        float[][] gesture = new float[1][20];

        tflite.run(ret, gesture);

        return gesture;
    }

    public void addSensorData(float x_axis, float y_axis, float z_axis){
        xText.setText("X: " + x_axis);
        x.add(x_axis);
        yText.setText("Y: " + y_axis);
        y.add(y_axis);
        zText.setText("Z: " + z_axis);
        z.add(z_axis);
        count += 1;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long currentTimeMillis = System.currentTimeMillis();

        // Trying to add a sample every ~100ms / sampling rate ~10Hz
        if(last_time_stamp_added==0){
            last_time_stamp_added=currentTimeMillis;
            addSensorData(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
        else if(currentTimeMillis-last_time_stamp_added<100){
            last_time_stamp = currentTimeMillis;
            temp_x = sensorEvent.values[0];
            temp_y = sensorEvent.values[1];
            temp_z = sensorEvent.values[2];
        }
        else{
            if(Math.abs(last_time_stamp-last_time_stamp_added-100) <= currentTimeMillis-last_time_stamp_added-100){
                last_time_stamp_added=last_time_stamp;
                addSensorData(temp_x, temp_y, temp_z);
                last_time_stamp=currentTimeMillis;
                temp_x = sensorEvent.values[0];
                temp_y = sensorEvent.values[1];
                temp_z = sensorEvent.values[2];
            }
            else{
                last_time_stamp_added=currentTimeMillis;
                addSensorData(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    /** Memory-map the model file in Assets. */
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private ArrayList<Float> multilevel_haar_transform(ArrayList<Float> a) {
        int level = 3;
        ArrayList<Float> final_array = new ArrayList<Float>();
        final_array = haar_transform(a);

        if(level>1){
            ArrayList<Float> array = new ArrayList<Float>();
            ArrayList<Float> left_array = new ArrayList<Float>();
            ArrayList<Float> right_array = new ArrayList<Float>();

            array=final_array;

            for(int i=1; i<level; i++){
                ArrayList<Float> half_left_array = new ArrayList<Float>();
                ArrayList<Float> half_right_array = new ArrayList<Float>();
                for (int j=0; j<array.size()/2; j++){
                    half_left_array.add(array.get(j));
                }

                for (int k=array.size()/2; k<array.size(); k++)
                {
                    half_right_array.add(array.get(k));
                }

                array = haar_transform(half_left_array);
                ArrayList<Float> tmp_right_array = new ArrayList<Float>();
                tmp_right_array.addAll(half_right_array);
                tmp_right_array.addAll(right_array);
                right_array = tmp_right_array;
            }

            array.addAll(right_array);
            final_array=array;
        }
        return final_array;
    }

    private ArrayList<Float> haar_transform(ArrayList<Float> arrlist) {
        ArrayList<Float> arrlist_haar = new ArrayList<Float>();
        double square_of2 = Math.sqrt(2);
        for (int counter = 0; counter < arrlist.size(); counter++) {
            float a = arrlist.get(counter);
            counter++;
            if(counter<arrlist.size()){
                float b = arrlist.get(counter);
                arrlist_haar.add((a+b)/(float)square_of2);
            }
            else{
                //symmetric extension mode
                arrlist_haar.add((a+a)/(float)square_of2);
            }

        }

        for (int counter = 0; counter < arrlist.size(); counter++) {
            float a = arrlist.get(counter);
            counter++;
            if(counter<arrlist.size()){
                float b = arrlist.get(counter);
                arrlist_haar.add((a-b)/(float)square_of2);
            }
            else{
                //symmetric extension mode
                arrlist_haar.add((float)0);
            }

        }
        return arrlist_haar;
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

}