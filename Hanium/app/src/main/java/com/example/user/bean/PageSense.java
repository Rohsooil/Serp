package com.example.user.bean;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class PageSense extends Fragment {
    ViewGroup v;
    ListView listView;
    ArrayList<Message> messageList;
    MyAdapter adapter;
    MainActivity mainActivity;
    //
    SQLiteDatabase sqldb;
    MessageDB messageDB;
    //
    DBHelper dbHelper;
    UserInfo user;

    public interface OnnListener{
        public void Onn(String value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnnListener){
            onnListener=(OnnListener) context;
        }else {
            throw new RuntimeException(context.toString()
                    +"must implement OnnListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        onnListener=null;
    }

    private OnnListener onnListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment_page_message,container,false);
        messageList = new ArrayList<Message>();
        //messageList.add(new Message("김희재","오후1시"));
        //messageList.add(new Message("정일형","오전2시"));
        //messageList.add(new Message("정태용","오후1시"));
        //messageList.add(new Message("한상빈","오전1시"));
        //messageList.add(new Message("노수일","오후11시"));
        mainActivity=(MainActivity)getActivity();

        user=mainActivity.getUserInfo();
        String myid=user.getUserId();
        Toast.makeText(getContext(),myid,Toast.LENGTH_LONG).show();
        dbHelper = new DBHelper(getContext());
        sqldb = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqldb.rawQuery("SELECT * FROM chattings", null);

        while (cursor.moveToNext()){
            if(myid.equals(cursor.getString(1))){
                Toast.makeText(getContext(),cursor.getString(3),Toast.LENGTH_LONG).show();
            }

            //int i=0;
            //messageList.add(new Message(cursor.getString(0),cursor.getString(2),cursor.getString(3)));
            //Toast.makeText(getContext(), messageList.get(i).getSender(), Toast.LENGTH_SHORT).show();

        }
        sqldb.close();
        cursor.close();

        listView = (ListView) v.findViewById(R.id.msgList);
        adapter = new MyAdapter(getContext(),R.layout.list_message,messageList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ((OnnListener) onnListener).Onn("문자열");
                onnListener.Onn("해헤");
                mainActivity = (MainActivity) getActivity();
                mainActivity.goDetailMessage("헤헤");
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
            messageLayout.setContentText(message.getContent());
            return messageLayout;
        }
    }
}
