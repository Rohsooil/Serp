package com.example.food8.projectofgimal;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.food8.projectofgimal.R.id.map;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    GpsInfo gpsInfo;
    Button locationReal;
    double latitude, longitude;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(map);
        mapFragment.getMapAsync(this);
        int permissionCheck = ContextCompat.checkSelfPermission(this,Manifest.permission_group.LOCATION);
        // this, thisActivity는 현재 활동입니다.
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 ok", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "권한 Nope", Toast.LENGTH_LONG).show();
            // 설명을 보여줘야합니까?
            if (ActivityCompat.shouldShowRequestPermissionRationale (this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "권한 NEED", Toast.LENGTH_LONG).show();
                // 사용자에게 비동기로 설명 표시 * * 차단하지 않음
                //이 스레드는 사용자의 응답을 기다린다!  사용자 후
                // 설명을보고, 다시 권한을 요청하십시오.

            } else {

                // 설명이 필요 없으면 우리는 허가를 요청할 수 있습니다.
                ActivityCompat.requestPermissions (this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS은 (는)
                // app-defined int 상수.  콜백 메소드는
                // 요청의 결과.
            }
        }

        locationReal = (Button) findViewById(R.id.locationReal);
        locationReal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gpsInfo = new GpsInfo(MainActivity.this);
                longitude = gpsInfo.getLongitude();
                latitude = gpsInfo.getLatitude();
                Log.d("stasdf", latitude + "   " + longitude);
                /*LatLng KKK = new LatLng(latitude, longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(KKK);
                markerOptions.title("현재");
                markerOptions.snippet("현재 위치");
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(KKK));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));*/
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap map) {

            gpsInfo = new GpsInfo(MainActivity.this);
            longitude = gpsInfo.getLongitude();
            latitude = gpsInfo.getLatitude();
            LatLng SEOUL = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(SEOUL);
            markerOptions.title("현재");
            markerOptions.snippet("현재 위치");
            map.addMarker(markerOptions);

            map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
            map.animateCamera(CameraUpdateFactory.zoomTo(18));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 승인 OK", Toast.LENGTH_LONG).show();// 권한 허가
// 해당 권한을 사용해서 작업을 진행할 수 있습니다
                } else {
                    Toast.makeText(this, "권한 거부", Toast.LENGTH_LONG).show();// 권한 거부
// 사용자가 해당권한을 거부했을때 해주어야 할 동작을 수행합니다
                }
                return;
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


}