package com.example.sanal.smartlamp;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sanal on 3/18/17.
 */

public class LoginGetRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://128.107.1.74:8443/user";
//        private static final String REGISTER_REQUEST_URL = "http://10.0.2.2:2403/user";


    public LoginGetRequest(String username, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET, LOGIN_REQUEST_URL + "/"+ username, listener, errorListener);
        Log.i("LoginGetRequest","Geting user data for " + username);
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
