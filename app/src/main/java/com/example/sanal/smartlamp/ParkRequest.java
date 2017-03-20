package com.example.sanal.smartlamp;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sanal on 3/18/17.
 */

public class ParkRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://128.107.1.74:8443/user";
//        private static final String REGISTER_REQUEST_URL = "http://10.0.2.2:2403/user";

    private Map<String, String> params;

    public ParkRequest(String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, LOGIN_REQUEST_URL+"/" + username, listener, errorListener);
        params = new HashMap<>();
        params.put("username", username.toString());
        params.put("password", password.toString());
        Log.i("poste",params.toString());
    }


    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
