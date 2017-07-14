package com.example.user.hanium;

import android.content.Context;
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

public class PageMessage extends Fragment {
    ViewGroup v;
    ListView listView;
    ArrayList<Message> messageList;
    MyAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment_page_message,container,false);
        messageList = new ArrayList<Message>();
        messageList.add(new Message("김희재","오후1시"));
        messageList.add(new Message("정일형","오전2시"));
        messageList.add(new Message("정태용","오후1시"));
        messageList.add(new Message("한상빈","오전1시"));
        messageList.add(new Message("노수일","오후11시"));

        listView = (ListView) v.findViewById(R.id.msgList);
        adapter = new MyAdapter(getContext(),R.layout.list_message,messageList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = (Message) adapter.getItem(position);
                Toast.makeText(getContext(),message.getSender(),Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }

    class MyAdapter extends BaseAdapter {
        Context mcontext;
        int list_message;
        ArrayList<Message> messageList;
        LayoutInflater layoutInflater;

        public MyAdapter(Context context, int list_friend, ArrayList<Message> messageList){
            mcontext = context;
            this.list_message = list_friend;
            this.messageList = messageList;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int position) {
            return messageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MessageLayout messageLayout = null;
            if(convertView == null){
                messageLayout = new MessageLayout(mcontext);
            } else {
                messageLayout = (MessageLayout)convertView;
            }
            Message message = messageList.get(position);
            messageLayout.setSender(message.getSender());
            messageLayout.setTime(message.getTime());
            return messageLayout;
        }
    }
}
