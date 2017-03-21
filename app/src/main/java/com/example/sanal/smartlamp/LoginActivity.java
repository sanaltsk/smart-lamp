package com.example.sanal.smartlamp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements LocationListener{
    LocationManager locationManager;
    String mprovider;
    double lat=0,lon=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new NukeSSLCerts().nuke();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        mprovider = locationManager.getBestProvider(criteria, false);

        if (mprovider != null && !mprovider.equals("")) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(mprovider);
            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);

            if (location != null)
                onLocationChanged(location);
            else
                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
        }

        final EditText etUsername = (EditText) findViewById(R.id.etLoginUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etLoginPassword);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterHere);
        final Button btnLogin = (Button) findViewById(R.id.loginBtn);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("Login", "Fetched User Data " + jsonObject.toString());
                            if(jsonObject.length()>0 && lat != 0 && lon != 0) {
                                String name = jsonObject.getString("billingContact");
                                String licensePlate =jsonObject.getString("carLicensePlat");
                                String zipCode = jsonObject.getString("zipcode");
                                Intent intent = new Intent(LoginActivity.this, MapsActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("name", name);
                                intent.putExtra("licensePlate",licensePlate);
                                intent.putExtra("zipcode", zipCode);
                                intent.putExtra("lat",lat);
                                intent.putExtra("lon",lon);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                etPassword.setText("");
                                etUsername.setText("");
                                etUsername.requestFocus();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Invalid User!!!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            etPassword.setText("");
                            etUsername.setText("");
                            etUsername.requestFocus();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Invalid User!!!")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        etPassword.setText("");
                        etUsername.setText("");
                        etUsername.requestFocus();
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Login Failed ")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                };

                LoginGetRequest r = new LoginGetRequest(username, responseListener, errorListener);

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                Log.i("Login",r.toString());
                queue.add(r);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
