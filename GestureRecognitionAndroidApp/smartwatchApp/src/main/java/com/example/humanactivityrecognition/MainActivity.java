package com.example.humanactivityrecognition;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.res.AssetFileDescriptor;
import org.tensorflow.lite.Interpreter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements SensorEventListener {

    private  SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;
    Fragment2 fragment2 = new Fragment2();

    public Sensor mySensor;
    public SensorManager SM;

    Interpreter tflite;

    private Boolean recordingXYZ=false;
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

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        // Load Model
        try {
            tflite = new Interpreter(loadModelFile());

        } catch (Exception ex){
            ex.printStackTrace();
        }

        // Create Accelerometer Sensor Manager
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Enables Always-on
        //setAmbientEnabled();
    }

    private void setupViewPager(ViewPager ViewPager){
        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mSectionsStatePagerAdapter.addFragment(new Fragment1());
        mSectionsStatePagerAdapter.addFragment(new Fragment2());
        mSectionsStatePagerAdapter.addFragment(new Fragment3());
        mViewPager.setAdapter(mSectionsStatePagerAdapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
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

    /** Memory-map the model file in Assets. */
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("model.tflite");
        System.out.println(fileDescriptor.toString());
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    public void startSensor(){
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void stopSensor(){
        SM.unregisterListener(this);
    }

    public void findGesture(){
        last_time_stamp_added=0;
        Fragment3 fragment3 = (Fragment3) mSectionsStatePagerAdapter.getItem(2);

        if(count<8 || count>30){
            if(count<8) fragment3.wrongSpeed("Too fast, try slower");
            else if(count>30) fragment3.wrongSpeed("Too slow, try faster");
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

        fragment3.showGesture(String.format("%.2f", max_percentage*100) + "% sure gesture is:", gesture_class+1);
        count=0;
    }

    private void addSensorData(float x_axis, float y_axis, float z_axis){
        x.add(x_axis);
        y.add(y_axis);
        z.add(z_axis);
        count += 1;

        Fragment2 fragment2 = (Fragment2) mSectionsStatePagerAdapter.getItem(1);
        fragment2.changeAxisText(x_axis,y_axis,z_axis);
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
}
