package com.example.user.bean;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class PageSenseMap extends Fragment implements OnMapReadyCallback{

    MapView mapView;
    ViewGroup v;
    GoogleMap googleMap;
    GPSListener gpsListener;
    double latitude;
    double longitude;
    Marker marker;
    ArrayList<Marker> markerList;
    LocationManager manager;
    boolean isGpsEnable = false;
    boolean isNetEnable = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment_page_sense_map,container,false);
        gpsListener = new GPSListener();
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        markerList = new ArrayList<Marker>();   //마커의 위치를 수정하기 위해 배에 저장하고 삭제하는 방식으로 구현
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        startLocationService();
        googleMap = map;
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(18));

    }

    private class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            startLocationService();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("TTTTTTTTTTTTTTTTTTTTT",latitude + "    " + longitude);
            if(location.getAccuracy()<100) {        //GPS의 정확도가 100 이상으로 넘어가면 이상한 위치를 잡아옴(ex : 판교, 서울숲) 따라서, 정확도 100이 넘어가면
                //지도에 표시하지 않도록 설정
                LatLng KKK = new LatLng(latitude, longitude);
                if(markerList.isEmpty()) {

                }else {
                    markerList.get(0).remove(); //마커의 위치를 수정하기 위해 배에 저장하고 삭제하는 방식으로 구현
                }
                marker = googleMap.addMarker(new MarkerOptions().position(KKK).title("ㅇㅋ").snippet("사용자 현재 위치")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.man)));
                marker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(KKK));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
                Log.d("Tag", latitude + " " + longitude);

                markerList.add(0,marker);  //마커의 위치를 수정하기 위해 배에 저장하고 삭제하는 방식으로 구현
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