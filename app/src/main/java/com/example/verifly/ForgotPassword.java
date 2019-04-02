package com.example.verifly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private Button mPassReset;
    private EditText mUserEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mUserEmail = findViewById(R.id.emailText);
        mPassReset = findViewById(R.id.button_reset);
        mAuth = FirebaseAuth.getInstance();

        mPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();

            }
        });
    }

    private void sendEmail() {
//        String mEmail = mUserEmail.getText().toString();
//
//
//        if (TextUtils.isEmpty(mEmail)) {
//            Toast.makeText(getApplication(), "Enter your registered email address", Toast.LENGTH_SHORT).show();
//            return;
//        }
        //Invalid email address
//        if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
//            Toast.makeText(getApplicationContext(), "Enter a valid email address", Toast.LENGTH_LONG).show();
//            return;
//        }


        mAuth.sendPasswordResetEmail(mUserEmail.getText().toString())

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPassword.this,
                                    "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(ForgotPassword.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                });
    }
}
