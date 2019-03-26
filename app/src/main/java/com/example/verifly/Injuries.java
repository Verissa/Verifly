package com.example.verifly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Injuries extends AppCompatActivity implements View.OnClickListener {
    private ImageButton bruises, sprains, cuts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injuries);
        bruises = findViewById(R.id.imageButton_search);
        sprains = findViewById(R.id.imageButton_search1);
        cuts = findViewById(R.id.imageButton_search2);

        bruises.setOnClickListener(this);
        sprains.setOnClickListener(this);
        cuts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.imageButton_search:
                break;

            case R.id.imageButton_search1:
                break;

            case R.id.imageButton_search2:
                break;
        }

    }
}
