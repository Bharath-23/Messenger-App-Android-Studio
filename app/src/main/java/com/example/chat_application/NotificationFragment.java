package com.example.chat_application;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import retrofit2.http.Url;

import static android.app.Activity.RESULT_OK;
import static android.widget.Toast.LENGTH_SHORT;


public class NotificationFragment extends Fragment {

    private DatabaseReference myRef;
    HashMap<String,Object> notificationmap;
    String notificationmsg;
    String notificationmsg1;
    String username;
    EditText text1;
    Button sendnotify;
    private Button btnChoose;
    private ImageView image;
    private StorageReference ref;
    ContentResolver contentResolver;

    Task<byte[]> notifyimage;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {          //clt+o should be pressed if onViewCreated is not present
        super.onViewCreated(view, savedInstanceState);//do the same if findViewById is not available

        sendnotify =(Button)view.findViewById(R.id.sendimage);
        text1 =(EditText)view.findViewById(R.id.notifications);
        btnChoose = (Button)view.findViewById(R.id.button);
        image = (ImageView)view.findViewById(R.id.imageuploaded);

        username=getActivity().getIntent().getExtras().getString("username");

        FirebaseDatabase database = FirebaseDatabase.getInstance();                   //Meta data : this should be declared when we are sending any text to firebase using send buttons
        myRef = database.getReference("Notifications");                         //this should also be declared





        notificationmap = new HashMap<>();                                              //Even this should be declared with meta data



        List<Notification> notificationList = new ArrayList<>();
        ListView listViewContainer1 = (ListView)view.findViewById(R.id.container2);


        final NotificationAdapter adapter1 = new NotificationAdapter(getActivity(),R.layout.single_notify,notificationList);
        listViewContainer1.setAdapter(adapter1);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Notification n1 = dataSnapshot.getValue(Notification.class);
                adapter1.add(n1);
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



        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        String Date = CommonMethods.getCurrentDate();
        String Time = CommonMethods.getCurrentTime();




        sendnotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notificationmsg1 = text1.getText().toString().trim();

                if (notificationmsg1.compareToIgnoreCase("") != 0){
                    notificationmsg = text1.getText().toString().trim();
                    notificationmap.put("notification",notificationmsg);
                    notificationmap.put("username",username);
                    notificationmap.put("date",Date);
                    notificationmap.put("time",Time);
                    notificationmap.put("image",notifyimage);

                    uploadfile();


                    FirebaseDatabase firebaseDatabase1 = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference1 = firebaseDatabase1.getReference().child("Notifications");
                    databaseReference1.push().setValue(notificationmap);





                    text1.setText("");


                }


            }
        });

    }



   public void uploadfile(){
        if (mImageUri != null){
            ref = FirebaseStorage.getInstance().getReference("Notifications");

            ref.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }




    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                    && data.getData() != null){
            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);


    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }
}

class CommonMethods {
    private static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    private static DateFormat timeFormat = new SimpleDateFormat("K:mma");

    public static String getCurrentTime() {

        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }

    public static String getCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);
    }

}
