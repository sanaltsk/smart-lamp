package com.example.sanal.smartlamp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sanal on 3/18/17.
 */

public class RegisterPutRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://128.107.1.74:8443/user";
//        private static final String REGISTER_REQUEST_URL = "http://10.0.2.2:2403/user";

    final String mRequestBody;
    public RegisterPutRequest(String username, String name, String address, String phone, String licensePlate, Response.Listener<String> listener, Response.ErrorListener errorListener) throws JSONException {
        super(Method.PUT, LOGIN_REQUEST_URL + "/" + username  + "/profile", listener, errorListener);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("billingContact", name);
            jsonBody.put("carLicensePlat", licensePlate);
            jsonBody.put("address", address.toString());
            jsonBody.put("phone", phone.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mRequestBody = jsonBody.toString();

        Log.i("RegisterPutRequest",jsonBody.toString());
    }


    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
            return null;
        }
    }

}
