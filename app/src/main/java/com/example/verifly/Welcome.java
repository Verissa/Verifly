package com.example.verifly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Welcome extends AppCompatActivity implements View.OnClickListener{
    private ImageButton request, quiz, learn;
    private TextView mUserName;
    private String mUserId, mName;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        request = findViewById(R.id.imageButton_search);
        quiz = findViewById(R.id.imageButton_search1);
        learn = findViewById(R.id.imageButton_search2);

        request.setOnClickListener(this);
        quiz.setOnClickListener(this);
        learn.setOnClickListener(this);
        mUserName = findViewById(R.id.main_toolbar_name);


        Intent mIntent = getIntent();
        String previousActivity= mIntent.getStringExtra("FROM_ACTIVITY");

        if (previousActivity.equals("Main")){
            String mUserId = mIntent.getStringExtra("User_ID");
            String userName = mIntent.getStringExtra("User_Name");
            mUserName.setText(userName);
        }

//        if (previousActivity.equals("MedicalInfo")){
//            String mUserId = mIntent.getStringExtra("User_ID");
//            String userName = mIntent.getStringExtra("User_Name");
//            mUserName.setText(userName);
//        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imageButton_search:
                Intent mIntent = new Intent(Welcome.this, Request.class);
                mIntent.putExtra("User_ID", mUserId);
                startActivity(mIntent);
                break;

            case R.id.imageButton_search1:
                Intent mIntent1 = new Intent(Welcome.this, Quiz.class);
                mIntent1.putExtra("User_ID", mUserId);
                startActivity(mIntent1);
                break;

            case R.id.imageButton_search2:
                startActivity(new Intent(this, Learn.class));
                break;


        }

    }


}
