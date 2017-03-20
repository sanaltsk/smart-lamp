package com.example.sanal.smartlamp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Random;

public class ServiceList extends AppCompatActivity {
    String [] names = {"Oil1","Oil2","Oil3","Gas1","Gas2","Gas3"};
    String [] prices = {"$10", "$20","$30","$4", "$5",""};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        ListView listView = (ListView) findViewById(R.id.listView);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            int max = 5, min = 1;
            convertView = getLayoutInflater().inflate(R.layout.custom_layout, null);
            TextView serviceName = (TextView) convertView.findViewById(R.id.tvServiceName);
            TextView desc = (TextView) convertView.findViewById(R.id.tvDesc);
            RatingBar rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
            rating.setNumStars(5);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceList.this);
                    builder.setMessage("Confirming the Service : " + names[position] +" request")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //Todo do rest call
                                    Intent intent = new Intent(ServiceList.this, FinalScreen.class);
                                    intent.addFlags(1);
                                    ServiceList.this.startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            });
            serviceName.setText(names[position]);
            desc.setText(prices[position]);
            Random rand = new Random();
            int value = rand.nextInt(5);
            rating.setRating(rand.nextInt((max - min) + 1) + min);
            return convertView;
        }
    }
}
