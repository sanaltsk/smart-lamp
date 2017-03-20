package com.example.sanal.smartlamp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double lat,lon;
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

        Button parkButton = (Button) findViewById(R.id.parkBtn);
        parkButton.setOnClickListener(onClickParkButton);
    }

    private View.OnClickListener onClickParkButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO Update REST call with parking id
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
            //TODO Update REST call with parking id
            Intent intent = new Intent(MapsActivity.this, FinalScreen.class);
            MapsActivity.this.startActivity(intent);
        }
    };

    private DialogInterface.OnClickListener onClickAddServices = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //TODO Update REST call with parking id and go to service intent
            Intent intent = new Intent(MapsActivity.this, ServiceList.class);
            MapsActivity.this.startActivity(intent);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
mMap.setBuildingsEnabled(true);
        LatLng currentLocation = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are parked here!!")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
        );


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentLocation)
                .zoom(19).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
