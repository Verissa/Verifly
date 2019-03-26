package com.example.verifly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private EditText mEmail, mName, mPassword;
    private Button mNext;
    private TextView mExists;
    private FirebaseAuth mUserAuth;
    private DatabaseReference mDatabase, mNode;


    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.passwd);

        mNext = findViewById(R.id.button_next);
        mExists = findViewById(R.id.textview_userexists);

        mNext.setOnClickListener(this);
        mExists.setOnClickListener(this);

        mUserAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //mNode = mDatabase.child("users");


    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch(id){
            case R.id.button_next:
                register();
                break;
            case R.id.textview_userexists:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

    }

    private void register() {
        final String email = mEmail.getText().toString();
        final String name = mName.getText().toString();
        String password = mPassword.getText().toString();


        //No email address entered by user
        if (TextUtils.isEmpty(name)){
            Toast.makeText(getApplicationContext(), "Enter your name", Toast.LENGTH_LONG).show();
            return;
        }


        //No email address entered by user
        if (TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(), "Enter an email address", Toast.LENGTH_LONG).show();
            return;
        }

        //Invalid email address
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(), "Enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }

        //No password entered by user
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_LONG).show();
            return;
        }

        //password length is less than 6 characters
        if (password.length()< 6){
            Toast.makeText(getApplicationContext(), "Password should have at least 6 characters", Toast.LENGTH_LONG).show();
            return;
        }

        mUserAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // Toast.makeText(getApplicationContext(), "User created", Toast.LENGTH_LONG).show();

                        FirebaseUser user = mUserAuth.getCurrentUser();
                        String userId = user.getUid();
//
//                        User mUser = new User(name, email);
//                        //mDatabase.push().setValue(mUser);
//
//                        mDatabase.child("users").child(userId).setValue(mUser);
//                        //mDatabase.child("users").child(mUserId).setValue(medicinfo1);

                        Intent mIntent = new Intent(Registration.this,MedicalInfo.class);
                        mIntent.putExtra("User_Name", name);
                        mIntent.putExtra("User_Email", email);


                        mIntent.putExtra("User_ID", userId);

                        startActivity(mIntent);



                    }
                });
    }
//    @IgnoreExtraProperties
//    public class User {
//
//        public String username;
//        public String email;
//
//
//        public User() {
//            // Default constructor required for calls to DataSnapshot.getValue(User.class)
//        }
//
//        public User(String username, String email) {
//            this.username = username;
//            this.email = email;
//        }
//
//    }
}
