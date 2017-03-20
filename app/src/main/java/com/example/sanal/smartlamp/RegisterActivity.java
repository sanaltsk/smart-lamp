package com.example.sanal.smartlamp;

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
        final EditText etMail = (EditText) findViewById(R.id.etMail);
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
                final String email = etMail.getText().toString();
                final String phone = etPhone.getText().toString();
                final String licensePlate = etLicensePlate.getText().toString();
                final Boolean shareServiceUsage = etShareServiceUsage.isChecked();
                final Boolean shareLicensePlate = etShareLicensePlate.isChecked();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.i("RegisterActivity",jsonObject.toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("RegisterActivity",error.toString());

                    }
                };

//                InputStream keyStore = getResources().openRawResource(R.raw.my);

                RegisterRequest r = new RegisterRequest(username, password, responseListener, errorListener);

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                Log.i("RegisterActivity",r.getBodyContentType().toString());
                queue.add(r);

            }
        });
    }
}
