package com.example.user.bean;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class PageChatting extends Fragment {
    ViewGroup v;
    ListView listView;
    ArrayList<Chat> chattingList;
    MyAdapter adapter;
    MainActivity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment_page_chatting,container,false);
        //mainActivity = (MainActivity) getActivity();
        chattingList = new ArrayList<Chat>();
        chattingList.add(new Chat("김희재","오후1시"));
        chattingList.add(new Chat("정일형","오전2시"));
        chattingList.add(new Chat("정태용","오후1시"));
        chattingList.add(new Chat("한상빈","오전1시"));
        chattingList.add(new Chat("노수일","오후11시"));

        listView = (ListView) v.findViewById(R.id.chatList);
        adapter = new MyAdapter(getContext(),R.layout.list_chatting,chattingList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chat chat = (Chat) adapter.getItem(position);
                Toast.makeText(getContext(),chat.getSender(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),DetailChatting.class);
                intent.putExtra("chat",chat.getSender());
                startActivity(intent);
            }
        });
        return v;
    }

    class MyAdapter extends BaseAdapter {
        Context mcontext;
        int list_chat;
        ArrayList<Chat> chattingList;
        LayoutInflater layoutInflater;

        public MyAdapter(Context context, int list_chat, ArrayList<Chat> chattingList){
            mcontext = context;
            this.list_chat = list_chat;
            this.chattingList = chattingList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return chattingList.size();
        }

        @Override
        public Object getItem(int position) {
            return chattingList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChattingLayout chattingLayout = null;
            if(convertView == null){
                chattingLayout = new ChattingLayout(mcontext);
            } else {
                chattingLayout = (ChattingLayout) convertView;
            }
            Chat chat = chattingList.get(position);
            chattingLayout.setChatSender(chat.getSender());
            chattingLayout.setChatTime(chat.getTime());
            return chattingLayout;
        }
    }
}
