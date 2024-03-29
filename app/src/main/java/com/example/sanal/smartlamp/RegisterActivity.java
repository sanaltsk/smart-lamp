package com.example.sanal.smartlamp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        new NukeSSLCerts().nuke();

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText etPhone = (EditText) findViewById(R.id.etPhone);
        final EditText etLicensePlate = (EditText) findViewById(R.id.etLicense);
        final Button registerButton = (Button) findViewById(R.id.registerBtn);
        final Switch etShareServiceUsage = (Switch) findViewById(R.id.switchShareService);
        final Switch etShareLicensePlate = (Switch) findViewById(R.id.switchShareLicense);

        etName.requestFocus();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final String name = etName.getText().toString();
                final String address = etAddress.getText().toString();
                final String phone = etPhone.getText().toString();
                final String licensePlate = etLicensePlate.getText().toString();
                final Boolean shareServiceUsage = etShareServiceUsage.isChecked();
                final Boolean shareLicensePlate = etShareLicensePlate.isChecked();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String getSavedUser = jsonObject.getString("username");
                            if(getSavedUser.equals(username)){
                                RegisterPutRequest r = null;
                                try {
                                    r = new RegisterPutRequest(username, name, address, phone,licensePlate, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                            builder.setMessage("User Added Successfully")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                            RegisterActivity.this.startActivity(intent);
                                                        }
                                                    })
                                                    .create()
                                                    .show();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("RegisterActivityError",error.toString());
                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                            builder.setMessage("User Registration Failed")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                            RegisterActivity.this.startActivity(intent);
                                                        }
                                                    })
                                                    .create()
                                                    .show();
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                                Log.i("RegisterActivity",r.getBodyContentType().toString());
                                queue.add(r);


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("RegisterActivityError",error.toString());
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("User Registration Failed")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        RegisterActivity.this.startActivity(intent);
                                    }
                                })
                                .create()
                                .show();

                    }
                };


                RegisterRequest r = null;
                try {
                    r = new RegisterRequest(username, password, responseListener, errorListener);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                Log.i("RegisterActivity",r.getBodyContentType().toString());
                queue.add(r);

            }
        });
    }
}
