package com.example.sanal.smartlamp;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new NukeSSLCerts().nuke();

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
                            Log.i("Login Screen", "Fetched User Data " + jsonObject.toString());
                            if(jsonObject.length()>0) {
                                String name = jsonObject.getString("billingContact");
                                String licensePlate =jsonObject.getString("carLicensePlat");
                                String zipCode = jsonObject.getString("zipcode");
                                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                                intent.putExtra("username", username);
                                intent.putExtra("name", name);
                                intent.putExtra("licensePlate",licensePlate);
                                intent.putExtra("zipcode", zipCode);
                                LoginActivity.this.startActivity(intent);
                            } else {
                                etPassword.setText("");
                                etUsername.setText("");
                                etUsername.requestFocus();
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Bad Response")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        builder.setMessage("Login Failed")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                };

                RegisterRequest r = new RegisterRequest(username, password, responseListener, errorListener);

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                Log.i("post1",r.toString());
                queue.add(r);
            }
        });
    }
}
