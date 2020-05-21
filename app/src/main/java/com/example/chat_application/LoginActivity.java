package com.example.chat_application;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TrueSDK;
import com.truecaller.android.sdk.TrueSdkScope;

public class LoginActivity extends AppCompatActivity {
    String USERNAME;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        TrueSDK.getInstance().onActivityResultObtained(this,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText username=(EditText)findViewById(R.id.e1);
        Button submit1=(Button)findViewById(R.id.submit);
        Button tlogin=(Button)findViewById(R.id.truelogin);



       TrueSdkScope trueScope = new TrueSdkScope.Builder(this, new ITrueCallback() {
            @Override
            public void onSuccessProfileShared(@NonNull TrueProfile trueProfile) {
                Intent intent=new Intent(LoginActivity.this,ChatActivity.class);
                intent.putExtra("username",trueProfile.firstName.toString());
                Toast.makeText(getApplicationContext(),trueProfile.firstName,Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailureProfileShared(@NonNull TrueError trueError) {

            }

            @Override
            public void onVerificationRequired() {

            }
        })
                .consentMode(TrueSdkScope.CONSENT_MODE_FULLSCREEN )
                .consentTitleOption( TrueSdkScope.SDK_CONSENT_TITLE_VERIFY )
                .footerType( TrueSdkScope.FOOTER_TYPE_SKIP )
                .build();

        TrueSDK.init(trueScope);

        tlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrueSDK.getInstance().getUserProfile(LoginActivity.this);
            }
        });

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USERNAME=username.getText().toString().trim();
                if (USERNAME.compareToIgnoreCase("")==0){
                    Toast.makeText(getApplicationContext(),"Enter Username",Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent=new Intent(LoginActivity.this,ChatActivity.class);
                    intent.putExtra("username",username.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }

            }
        });


    }
}
