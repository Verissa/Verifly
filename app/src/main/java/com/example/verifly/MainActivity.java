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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mForgotPass;
    private Button mLogin, mNewUser;
    private EditText mUserEmail, mUserPass;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        mForgotPass = findViewById(R.id.textview_forgotpassword);
        mLogin = findViewById(R.id.button_login);
        mNewUser = findViewById(R.id.button_register);

        mForgotPass.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mNewUser.setOnClickListener(this);

        mUserEmail = findViewById(R.id.emailText);
        mUserPass = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.button_login:
                userLogin();
                break;

            case R.id.button_register:
                startActivity(new Intent(this, Registration.class));
                break;

            case R.id.textview_forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;


        }

    }

    private void userLogin() {
        String userEmail = mUserEmail.getText().toString();
        String userPassword = mUserPass.getText().toString();

        //No email address entered by user
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(getApplicationContext(), "Enter an email address", Toast.LENGTH_LONG).show();
            return;
        }

        //Invalid email address
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            Toast.makeText(getApplicationContext(), "Enter a valid email address", Toast.LENGTH_LONG).show();
            return;
        }

        //No password entered by user
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(getApplicationContext(), "Enter a password", Toast.LENGTH_LONG).show();
            return;
        }

        //password length is less than 6 characters
        if (userPassword.length()< 6){
            Toast.makeText(getApplicationContext(), "Password should have at least 6 characters", Toast.LENGTH_LONG).show();
            return;
        }



        mAuth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //startActivity(new Intent(MainActivity.this, TutorialActivity.class));

                            //Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String mUserId = user.getUid();
                            String mUserName = user.getDisplayName();
                            Intent mIntent = new Intent(MainActivity.this, Welcome.class);
                            mIntent.putExtra("FROM_ACTIVITY", "Main");
                            mIntent.putExtra("User_ID", mUserId);
                            mIntent.putExtra("User_Name", mUserName);
                            startActivity(mIntent);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseAuthInvalidUserException) {

                    String errorCode =
                            ((FirebaseAuthInvalidUserException) e).getErrorCode();

                    if (errorCode.equals("ERROR_USER_NOT_FOUND")) {
                        Toast.makeText(getApplicationContext(), "No matching account found", Toast.LENGTH_LONG).show();
                    } else if (errorCode.equals("ERROR_USER_DISABLED")) {
                        Toast.makeText(getApplicationContext(), "User account has been disabled", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }






        });
    }
}
