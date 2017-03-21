package com.example.sanal.smartlamp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Random;

public class ServiceList extends AppCompatActivity {
    String [] names = {"Oil1","Oil2","Oil3","Gas1","Gas2","Gas3"};
    String [] vendors = {"Shell","Sunshine","Chevron","Shell","Sunshine","Chevron"};
    String [] prices = {"$10", "$20","$30","$4", "$5",""};
    String username,name,licensePlate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        ListView listView = (ListView) findViewById(R.id.listView);

        CustomAdapter customAdapter = new CustomAdapter();

        listView.setAdapter(customAdapter);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        licensePlate = intent.getStringExtra("licensePlate");
        name = intent.getStringExtra("name");
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
            TextView price = (TextView) convertView.findViewById(R.id.tvPrice);
            RatingBar rating = (RatingBar) convertView.findViewById(R.id.ratingBar);
            rating.setNumStars(5);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceList.this);
                    builder.setMessage("Confirming the Service : " + names[position] +" request")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        ServiceRequest sr = new ServiceRequest(username, vendors[position], names[position], new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                Log.i("ServiceRequest", "Service Request Done for " + username);
                                                Intent intent = new Intent(ServiceList.this, FinalScreen.class);
                                                intent.addFlags(1);
                                                ServiceList.this.startActivity(intent);
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Log.i("ServiceRequest","Service Request Failed ");
                                            }
                                        });
                                        RequestQueue queue = Volley.newRequestQueue(ServiceList.this);
                                        Log.i("ServiceList",sr.getBodyContentType().toString());
                                        queue.add(sr);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            });
            serviceName.setText(names[position]);
            price.setText(prices[position]);
            desc.setText(vendors[position]);
            Random rand = new Random();
            int value = rand.nextInt(5);
            rating.setRating(rand.nextInt((max - min) + 1) + min);
            return convertView;
        }
    }
}
