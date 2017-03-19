package com.example.sanal.smartlamp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FinalScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);
        Toast.makeText(FinalScreen.this, "Car parked successfully!",
                Toast.LENGTH_LONG).show();
    }
}
