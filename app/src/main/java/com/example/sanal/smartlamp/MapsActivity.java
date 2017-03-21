package com.example.sanal.smartlamp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat,lon;
    String username,licensePlate, name, parkingId="A123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat",37.414601037);
        lon = intent.getDoubleExtra("lon",-121.9369120);

        username = intent.getStringExtra("username");
        name = intent.getStringExtra("name");
        licensePlate = intent.getStringExtra("licensePlate");



        Button parkButton = (Button) findViewById(R.id.parkBtn);
        parkButton.setOnClickListener(onClickParkButton);
    }

    private View.OnClickListener onClickParkButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
            builder.setMessage("Do you want to add some services while you are parked??")
                    .setPositiveButton("Yes!!", onClickAddServices)
                    .setNegativeButton("Just Park", onClickJustParkAlert)
                    .create()
                    .show();
        }
    };

    private DialogInterface.OnClickListener onClickJustParkAlert = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ParkRequest r = null;
            try {
                r = new ParkRequest(username, parkingId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
            Log.i("RegisterActivity",r.getBodyContentType().toString());
            queue.add(r);


            Intent intent = new Intent(MapsActivity.this, FinalScreen.class);
            MapsActivity.this.startActivity(intent);
        }
    };

    private DialogInterface.OnClickListener onClickAddServices = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            ParkRequest r = null;
            try {
                r = new ParkRequest(username, parkingId);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);
            Log.i("RegisterActivity",r.getBodyContentType().toString());
            queue.add(r);

            Intent intent = new Intent(MapsActivity.this, ServiceList.class);
            intent.putExtra("username", username);
            intent.putExtra("licensePlate", licensePlate);
            intent.putExtra("name", name);
            MapsActivity.this.startActivity(intent);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng currentLocation = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions().rotation(-20).position(currentLocation).title("You are parked here!!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.smallcar)));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocation)
                .zoom(19).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
