package com.example.chat_application;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class ChatFragment extends Fragment {

    DatabaseReference myRef;
    HashMap<String,Object> messagemap;
    String message;
    String message1;
    String username;
    EditText text;
    Button send;
    Boolean ismine = true;




    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        send=(Button)view.findViewById(R.id.send);
        text=(EditText)view.findViewById(R.id.text);


        username=getActivity().getIntent().getExtras().getString("username");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Messages");

        messagemap = new HashMap<>();

        List<Message> messageList = new ArrayList<>();
        ListView listViewContainer = (ListView)view.findViewById(R.id.container);



        final MessageAdapter adapter = new MessageAdapter(getActivity(),R.layout.single_message,messageList);
        listViewContainer.setAdapter(adapter);




        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Message n = dataSnapshot.getValue(Message.class);
                adapter.add(n);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        String time1 = CommonMethods.getCurrentTime();



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                message1=text.getText().toString().trim();

                if (message1.compareToIgnoreCase("")==0){
                    Toast.makeText(getActivity(),"Enter Message",Toast.LENGTH_LONG).show();
                }
                else{
                    message=text.getText().toString().trim();
                    messagemap.put("message",message);
                    messagemap.put("username",username);
                    messagemap.put("ismine",ismine);
                    messagemap.put("time",time1);


                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference().child("Messages");
                    databaseReference.push().setValue(messagemap);

                    text.setText("");
                }


            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);


    }

}
