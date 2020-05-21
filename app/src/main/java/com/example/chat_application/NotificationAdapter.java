package com.example.chat_application;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    public NotificationAdapter(Context context, int resource, List<Notification> objects){
        super(context,resource,objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.single_notify,parent,false);
        }

        Notification n1 = getItem(position);

        TextView singleusername = (TextView)convertView.findViewById(R.id.sender);
        singleusername.setText(n1.getUsername());

        TextView notificationcontent = (TextView)convertView.findViewById(R.id.notifications11);
        notificationcontent.setText(n1.getNotification());

        TextView date = (TextView)convertView.findViewById(R.id.date);
        date.setText(n1.getDate());

        TextView time = (TextView)convertView.findViewById(R.id.time);
        time.setText(n1.getTime());


        return convertView;


    }
}
