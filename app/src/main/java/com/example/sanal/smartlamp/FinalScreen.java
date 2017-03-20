package com.example.sanal.smartlamp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FinalScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        Intent intent = getIntent();
        String message;
        int fromServiceList = intent.getFlags();
        if(fromServiceList==1) {
            message = "Car parked and orderd services successfully !";
        } else {
            message = "Car parked successfully!";
        }

        Toast.makeText(FinalScreen.this, message,
                Toast.LENGTH_LONG).show();
    }
}
