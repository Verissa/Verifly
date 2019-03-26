package com.example.verifly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicalInfo extends AppCompatActivity {
    private RadioButton mAns, mAns1;
    private RadioGroup mAllegryAnswer, mMedical;

    private EditText mAllergies, mMedicalCond;
    private Button mRegisterComplete;
    private FirebaseAuth mUserAuth;
    private DatabaseReference mDatabase;
    private String mUserId, name, email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_info);
        mAllegryAnswer = findViewById(R.id.rgroup);
        mMedical = findViewById(R.id.rgroup1);

        mAllergies = findViewById(R.id.allergy);
        mMedicalCond = findViewById(R.id.medical);

        Intent mIntent = getIntent();
        mUserId= mIntent.getStringExtra("User_ID");
        name =  mIntent.getStringExtra("User_Name");
        email =  mIntent.getStringExtra("User_Email");

        mRegisterComplete = findViewById(R.id.button_complete);
        mUserAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRegisterComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register2();
            }
        });
    }

    private void register2() {
        int selectedId = mAllegryAnswer.getCheckedRadioButtonId();
        mAns = (RadioButton) findViewById(selectedId);
        int selectedId1 = mMedical.getCheckedRadioButtonId();
        mAns1 = (RadioButton) findViewById(selectedId);


        final String allergy_answer = mAns.getText().toString();
        final String medical_condition_answer = mAns1.getText().toString();

        final String allergy = mAllergies.getText().toString();
        final String medical_condition = mMedicalCond.getText().toString();

        //No option selected by user
        if (TextUtils.isEmpty(allergy_answer)){
            Toast.makeText(getApplicationContext(), "Select an answer", Toast.LENGTH_LONG).show();
        }
        //No option selected by user
        if (TextUtils.isEmpty(medical_condition_answer)){
            Toast.makeText(getApplicationContext(), "Select an answer", Toast.LENGTH_LONG).show();
        }
//
//        //No email address entered by user
//        if (answer == "Yes" && TextUtils.isEmpty(allergy)){
//            Toast.makeText(getApplicationContext(), "Enter your allergies", Toast.LENGTH_LONG).show();
//        }
//        //No email address entered by user
//        if (answer1 == "Yes" && TextUtils.isEmpty(medical)){
//            Toast.makeText(getApplicationContext(), "Enter your medical condition", Toast.LENGTH_LONG).show();
//        }


        Medical medicinfo1 = new Medical(name, email, allergy_answer,allergy,medical_condition_answer,medical_condition);
        mDatabase.child("users").child(mUserId).setValue(medicinfo1);
        Intent mIntent = new Intent(MedicalInfo.this, Welcome.class);
        mIntent.putExtra("User_Name", name);

        mIntent.putExtra("User_ID", mUserId);

        startActivity(mIntent);





    }

    public class Medical {

        public String name, email, allergy_answer,allergy,medical_condition_answer,medical_condition;


        public Medical() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Medical(String name, String email, String allergy_answer,String allergy,String medical_condition_answer, String medical_condition) {
            this.name = name;
            this.email = email;
            this.allergy_answer = allergy_answer;
            this.allergy = allergy;
            this.medical_condition_answer = medical_condition_answer;
            this.medical_condition = medical_condition;
        }

    }
}