package com.example.food8.projectofgimal;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.food8.projectofgimal.R.id.map;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    ViewGroup v;

    String userName ="";
    GoogleMap googleMap;
    MapView mapView;
    GPSListener gpsListener;
    double latitude;
    double longitude;
    Marker marker;
    ArrayList<Marker> markerList;
    Switch aSwitch;
    LocationManager manager;
    boolean isGpsEnable = false;
    boolean isNetEnable = false;
    MainActivity mainActivity;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);
        aSwitch = (Switch) v.findViewById(R.id.switch1);
        gpsListener = new GPSListener();
        mapView = (MapView) v.findViewById(map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        userName = getArguments().getString("name");
        mainActivity = (MainActivity) getActivity();
        markerList = new ArrayList<Marker>();
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.goToCamera(userName);
            }
        });
        return v;
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        startLocationService();
        googleMap = map;
        //IT대학 마킹
        LatLng ITcol = new LatLng(37.4510857, 127.1271518);
        MarkerOptions markerIT = new MarkerOptions();
        markerIT.position(ITcol);
        markerIT.title("IT대학");
        markerIT.snippet(getResources().getString(R.string.it_college));
        map.addMarker(markerIT);
        map.addCircle(new CircleOptions().center(ITcol).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        map.moveCamera(CameraUpdateFactory.newLatLng(ITcol));
        map.animateCamera(CameraUpdateFactory.zoomTo(18));
        //국제어학원 마킹
        LatLng GLO = new LatLng(37.4519204, 127.1271411);
        MarkerOptions markerGLO = new MarkerOptions();
        markerGLO.position(GLO);
        markerGLO.title("국제어학원");
        markerGLO.snippet("국제어학원");
        map.addMarker(markerGLO);
        map.addCircle(new CircleOptions().center(GLO).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        //공과대학1 마킹
        LatLng ENG1 = new LatLng(37.4515541, 127.1281604);
        MarkerOptions markerENG = new MarkerOptions();
        markerENG.position(ENG1);
        markerENG.title("공과대학1");
        markerENG.snippet("공과대학1");
        map.addMarker(markerENG);
        map.addCircle(new CircleOptions().center(ENG1).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        //중앙도서관 마킹
        LatLng CENLIB = new LatLng(37.4522611, 127.1330098);
        MarkerOptions markerCLIB = new MarkerOptions();
        markerCLIB.position(CENLIB);
        markerCLIB.title("중앙도서관");
        markerCLIB.snippet("중앙도서관");
        map.addMarker(markerCLIB);
        map.addCircle(new CircleOptions().center(CENLIB).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        //학생회관 마킹
        LatLng STD = new LatLng(37.4531128, 127.1343080);
        MarkerOptions markerSTD = new MarkerOptions();
        markerSTD.position(STD);
        markerSTD.title("학생회관");
        markerSTD.snippet("학생회관");
        map.addMarker(markerSTD);
        map.addCircle(new CircleOptions().center(STD).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        //교육대학원 마킹
        LatLng EDU = new LatLng(37.4518182, 127.1317867);
        MarkerOptions markerEDU = new MarkerOptions();
        markerEDU.position(EDU);
        markerEDU.title("교육대학원");
        markerEDU.snippet("교육대학원");
        map.addMarker(markerEDU);
        map.addCircle(new CircleOptions().center(EDU).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));
        //비전타워 마킹
        LatLng VST = new LatLng(37.4493226, 127.1272484);
        MarkerOptions markerVST = new MarkerOptions();
        markerVST.position(VST);
        markerVST.title("비전타워");
        markerVST.snippet("비전타워");
        map.addMarker(markerVST);
        map.addCircle(new CircleOptions().center(VST).radius(30).strokeColor(Color.parseColor("#884169e1")).fillColor(Color.parseColor("#5587cefa")));


        //흡연장1
        LatLng smoking1 = new LatLng(37.4514562, 127.1272055);
        MarkerOptions markerSMK1 = new MarkerOptions();
        markerSMK1.position(smoking1);
        markerSMK1.title("흡연구역");
        markerSMK1.snippet("위험");
        markerSMK1.icon(BitmapDescriptorFactory.fromResource(R.drawable.smoking));
        map.addMarker(markerSMK1);
    }



    private class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            startLocationService();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if(location.getAccuracy()<100) {
                LatLng KKK = new LatLng(latitude, longitude);
                if(markerList.isEmpty()) {

                }else {
                    markerList.get(0).remove();
                }
                marker = googleMap.addMarker(new MarkerOptions().position(KKK).title(userName).snippet("사용자 현재 위치")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.man)));
                marker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(KKK));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                Log.d("Tag", latitude + " " + longitude);

                markerList.add(0,marker);
            }
            else{
                Toast.makeText(getContext(),"정확도 낮음",Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}

    }

    private void startLocationService() {
        // 위치 관리자 객체 참조
        manager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Location lastlocation = null;
        long minTime = 1000;//GPS정보 전달 시간 지정
        float minDistance = 0;//이동거리 지정

        //위치정보는 위치 프로바이더(Location Provider)를 통해 얻는다
        try {
            isGpsEnable = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetEnable = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            // GPS를 이용한 위치 요청

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener
            );
            //(3) ================
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener
            );

        } catch(SecurityException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        manager.removeUpdates(gpsListener);
        super.onPause();
    }
}
