package com.example.verifly;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Injuries extends AppCompatActivity implements View.OnClickListener {
    private ImageButton nosebleed, sprains, cuts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_injuries);
        nosebleed = findViewById(R.id.imageButton_search);
        sprains = findViewById(R.id.imageButton_search1);
        cuts = findViewById(R.id.imageButton_search2);

        nosebleed.setOnClickListener(this);
        sprains.setOnClickListener(this);
        cuts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.imageButton_search:
                Intent mIntent = new Intent(Injuries.this, Request.class);
                mIntent.putExtra("Injury", "Nosebleed");
                startActivity(mIntent);

                break;

            case R.id.imageButton_search1:
                Intent mIntent2 = new Intent(Injuries.this, Request.class);
                mIntent2.putExtra("Injury", "Sprains");
                startActivity(mIntent2);
                break;

            case R.id.imageButton_search2:
                Intent mIntent3 = new Intent(Injuries.this, Request.class);
                mIntent3.putExtra("Injury", "Cuts");
                startActivity(mIntent3);
                break;
        }

    }
}
