package com.example.food8.projectofgimal;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by food8 on 2017-06-02.
 */

public class CameraFragment extends Fragment {
    final static String TAG = "PAAR";
    SensorManager sensorManager;
    int orientationSensor;
    float headingAngle;
    float pitchAngle;
    float rollAngle;
    int accelerometerSensor;
    float xAxis;
    float yAxis;
    float zAxis;
    GPSListener gpsListener;
    SurfaceView cameraPreview;
    SurfaceHolder previewHolder;
    Camera camera;
    boolean inPreview;
    LocationManager manager;
    ImageView imageView;
    ImageView imageView2;
    private MediaPlayer mediaPlayer;
    Switch aSwitch;
    String userName ="";
    int aa=5;
    int bb=7;
    int cc=0;
    ViewGroup v;
    MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.camera_fragment, container, false);
        inPreview=false;
        userName = getArguments().getString("name");
        startLocationService();
        mainActivity = (MainActivity) getActivity();
        //mainActivity.textViewing();
        mediaPlayer = MediaPlayer.create(getActivity(),R.raw.bell);

        cameraPreview=(SurfaceView) v.findViewById (R.id.cameraPreview);
        previewHolder=cameraPreview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        orientationSensor = Sensor.TYPE_ORIENTATION;
        accelerometerSensor=Sensor.TYPE_ACCELEROMETER;
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(orientationSensor), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(accelerometerSensor),SensorManager.SENSOR_DELAY_NORMAL);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        aSwitch = (Switch) v.findViewById(R.id.switch2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goBackMap(userName);
            }
        });
        return v;
    }


    private void startLocationService() {
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        gpsListener = new GPSListener();
        long minTime = 1000;//GPS정보 전달 시간 지정 - 10초마다 위치정보 전달
        float minDistance = 0;//이동거리 지정
        try {

            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();
            }
        } catch(SecurityException ex) {
            ex.printStackTrace();
        }
    }


    private class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();//위도
            Double longitude = location.getLongitude();//경도

            cc++;
            if(latitude<37.4518016 && latitude>37.45056){
                if(longitude<127.13038 && longitude>127.1274597947){
                    mediaPlayer.start();
                    if(rollAngle>40){
                        if(headingAngle>140&&headingAngle<240){
                            //imageView.setVisibility(View.VISIBLE);
                            //imageView2.setVisibility(View.INVISIBLE);
                            mediaPlayer.start();
                            mainActivity.textViewing();
                            Toast.makeText(getContext(),"IT대학입니다.",Toast.LENGTH_LONG).show();
                        }
                        else if(headingAngle>0&&headingAngle<40){
                            Toast.makeText(getContext(),"공과대학띠~~~~~",Toast.LENGTH_LONG).show();
                            mediaPlayer.start();
                        }
                        else if(headingAngle>300&&headingAngle<360){
                            Toast.makeText(getContext(),"공과대학띠~~~~~",Toast.LENGTH_LONG).show();
                            mediaPlayer.start();
                        }
                    }
                }

            }
            if (latitude < 37.4508897 && latitude > 37.4439) {
                if (longitude < 127.128085622 && longitude > 127.127702) {
                    mediaPlayer.start();
                    if (rollAngle > 40) {
                        if (headingAngle > 140 && headingAngle < 240) {
                            mainActivity.viewing();
                            mediaPlayer.start();
                        }
                    }
                }
            }
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                headingAngle = sensorEvent.values[0];
                pitchAngle = sensorEvent.values[1];
                rollAngle = sensorEvent.values[2];
                if(rollAngle>70){
                }
            }
            else if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                xAxis=sensorEvent.values[0];
                yAxis=sensorEvent.values[1];
                zAxis=sensorEvent.values[2];
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume(){
        super.onResume();

        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor
                        (orientationSensor),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor
                        (accelerometerSensor),
                SensorManager.SENSOR_DELAY_NORMAL);
        camera= Camera.open();

    }

    @Override
    public void onPause(){
        if(inPreview){camera.stopPreview();}
        sensorManager.unregisterListener(sensorEventListener);
        camera.release(); camera=null;
        inPreview=false;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        manager.removeUpdates(gpsListener);
        super.onPause();
    }


    private Camera.Size getBestPreviewSize(int width,int height,Camera.Parameters parameters){
        Camera.Size result=null;
        for(Camera.Size size : parameters.getSupportedPreviewSizes()){
            if(size.width<=width&&size.height<=height){
                if(result==null){result=size;}else{int resultArea=result.width*result.height;
                    int newArea=size.width*size.height;if(newArea>resultArea){
                        result=size;}
                }
            }
        }
        return (result);}

    SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback(){
        public void surfaceCreated(SurfaceHolder holder){try{
            camera.setPreviewDisplay(previewHolder);}
        catch(Throwable t){
            Log.e("ProAndroidAR2Activity","Exception in setPreviewDisplay()",t);}}

        public void surfaceChanged(SurfaceHolder holder,int format,int width,int height) {
            Camera.Parameters parameters=camera.getParameters();
            Camera.Size size=getBestPreviewSize(width,height,parameters);
            if(size!=null){parameters.setPreviewSize(size.width,size.height);
                camera.setParameters(parameters);
                camera.startPreview();
                inPreview=true;}}
        public void surfaceDestroyed(SurfaceHolder holder){}

    };
}
