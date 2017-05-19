package com.example.food8.hooooooomework;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by food8 on 2017-05-16.
 */

public class MainFragment extends Fragment {
    ViewGroup v;
    ListView listView;
    MyAdapter adapter;
    ArrayList<SingerItem> album = new ArrayList<SingerItem>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment_list_view, container, false);
        album.add(new SingerItem(R.drawable.img01, "정은지", "에이핑크", "너란 봄"));
        album.add(new SingerItem(R.drawable.img02, "트와이스", "JYP 엔터테이먼트", "KNOCK KNOCK"));
        album.add(new SingerItem(R.drawable.img03, "하이라이트", "어라운드어스", "얼굴 찌푸리지 말아요"));
        album.add(new SingerItem(R.drawable.img04, "위너", "YG 엔터테이먼트", "REALLY REALLY"));
        album.add(new SingerItem(R.drawable.img05, "걸스데이", "드림티 엔터테인먼트", "I'LL BE YOURS"));
        album.add(new SingerItem(R.drawable.img06, "이엑스아이디", "바나나컬쳐 엔터테인먼트", "낮 보다는 밤"));
        album.add(new SingerItem(R.drawable.img07, "지코", "세븐시즌스", "SHES A BABY"));
        adapter = new MyAdapter(getContext(),R.layout.singer_item,album);
        listView = (ListView) v.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerItem curItem = (SingerItem) adapter.getItem(position);
                //getName() 메서드를 이용하여 아이템에서 이름을 가져옴
                String curName = curItem.getName();
                Toast.makeText(getContext(),"안녕하세요. " + curName + "입니다.",Toast.LENGTH_LONG).show();
                MainActivity mainActivity = (MainActivity) getActivity();

                mainActivity.showDetail(curItem);
            }
        });

        return v;
    }

    class MyAdapter extends BaseAdapter{
        Context mcontext;
        int singer_item;
        ArrayList<SingerItem> album;
        LayoutInflater layoutInflater;

        public MyAdapter(Context context, int singer_item, ArrayList<SingerItem> album){
            mcontext = context;
            this.singer_item = singer_item;
            this.album = album;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return album.size();
        }

        @Override
        public Object getItem(int position) {
            return album.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(SingerItem item){
            album.add(item);
        }

        public void clearItem(){
            album.clear();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerLayout singerLayout = null;

            if(convertView == null){
                singerLayout = new SingerLayout(mcontext);
            } else {
                singerLayout = (SingerLayout) convertView;
            }
            SingerItem item = album.get(position);

            singerLayout.setImage(item.getResId());
            singerLayout.setCompany(item.getCompany());
            singerLayout.setNameText(item.getName());
            singerLayout.setSong(item.getSong());
            return singerLayout;
        }

    }

}
