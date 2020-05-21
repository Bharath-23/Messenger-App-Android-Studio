package com.example.chat_application;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import static android.view.View.inflate;
import static java.util.zip.Inflater.*;

public class MessageAdapter extends ArrayAdapter<Message> {



    public MessageAdapter(Context context, int resource, List<Message> objects){
        super(context,resource,objects);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {



        if (convertView==null){
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.single_message,parent,false);
        }


        Message n = getItem(position);


        TextView singleUsername = (TextView)convertView.findViewById(R.id.Name);
        singleUsername.setText(n.getUsername());


        TextView singleMessage = (TextView)convertView.findViewById(R.id.msg);
        singleMessage.setText(n.getMessage());

        TextView singleTime = (TextView)convertView.findViewById(R.id.msgtime);
        singleTime.setText(n.getTime());





        /*Message n = getItem(position);


        View vi = convertView;
        if (convertView == null)
            vi = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.single_message,parent,false);

        TextView singleUsername = (TextView) vi.findViewById(R.id.Name);
        singleUsername.setText(n.getUsername());
        TextView singleMessage = (TextView)vi.findViewById(R.id.msg);
        singleMessage.setText(n.getMessage());
        LinearLayout layout = (LinearLayout) vi.findViewById(R.id.single_message1);
        LinearLayout parent_layout = (LinearLayout) vi.findViewById(R.id.single_message_parent);

        if (n.isMine){
            parent_layout.setGravity(Gravity.RIGHT);
        }
        else{
            parent_layout.setGravity(Gravity.LEFT);
        }*/



        singleMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),n.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }

}
