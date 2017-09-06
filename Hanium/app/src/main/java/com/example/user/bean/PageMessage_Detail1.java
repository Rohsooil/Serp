package com.example.user.bean;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.user.bean.R.id.map;


public class PageMessage_Detail1 extends AppCompatActivity implements OnMapReadyCallback
{

    String sss;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_message_detail1);
        Intent intent = getIntent();
        String value = intent.getStringExtra("value").toString();
        Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(map);
        mapFragment.getMapAsync(this);
        getAddress(this,37.56, 126.97);
        //findGeoPoint(this,sss);
    }

    public String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;
                    Toast.makeText(getApplicationContext(),"nowAddress:"+nowAddress,Toast.LENGTH_LONG).show();
                    sss=nowAddress;
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
        return nowAddress;
    }
    public Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lon = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lon);
                Toast.makeText(getApplicationContext(),"lat:"+lat,Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"lon:"+lon,Toast.LENGTH_LONG).show();

            }
        }
        return loc;
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng SEOUL = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){
                String text = "[단시간 클릭시 이벤트] latitude ="
                        + point.latitude + ", longitude ="
                        + point.longitude;
                getAddress(getApplicationContext(),point.latitude,point.longitude);
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();
            }

            //대한민국 서울특별시 중구의주로2가 16-8
            // Toast.makeText(getApplication(),"dd",Toast.LENGTH_LONG).show();
        });
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_4));
                markerOptions.position(latLng); //마커위치설정

                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));   // 마커생성위치로 이동
                map.addMarker(markerOptions); //마커 생성


            }
        });

    }

    /*@Override
    public void Onn(String value) {
        sss=value;
        Log.d("stringcheck","string value :"+sss);
    }*/
}
