package com.example.user.hanium;

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


public class PageFriend extends Fragment {
    ViewGroup v;
    ListView listView;
    ArrayList<Friend> friendList;
    MyAdapter adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup)inflater.inflate(R.layout.fragment_page_friend,container,false);
        friendList = new ArrayList<Friend>();
        friendList.add(new Friend("김희재"));
        friendList.add(new Friend("정일형"));
        friendList.add(new Friend("정태용"));
        friendList.add(new Friend("한상빈"));
        friendList.add(new Friend("노수일"));
        listView = (ListView) v.findViewById(R.id.friendList);
        adapter = new MyAdapter(getContext(),R.layout.list_friend,friendList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = (Friend) adapter.getItem(position);
                Toast.makeText(getContext(),friend.getName(),Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }

    class MyAdapter extends BaseAdapter{
        Context mcontext;
        int list_friend;
        ArrayList<Friend> friendList;
        LayoutInflater layoutInflater;

        public MyAdapter(Context context, int list_friend, ArrayList<Friend> friendList){
            mcontext = context;
            this.list_friend = list_friend;
            this.friendList = friendList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return friendList.size();
        }

        @Override
        public Object getItem(int position) {
            return friendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FriendLayout friendLayout = null;
            if(convertView == null){
                friendLayout = new FriendLayout(mcontext);
            } else {
                friendLayout = (FriendLayout)convertView;
            }
            Friend friend = friendList.get(position);
            friendLayout.setName(friend.getName());
            return friendLayout;
        }
    }

}
