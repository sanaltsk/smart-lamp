package com.example.sanal.smartlamp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by sanal on 3/18/17.
 */

public class ParkRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "https://128.107.1.74:8443/user";

    final String mRequestBody;
    public ParkRequest(final String username, String parkingId) throws JSONException {
        super(Method.POST, LOGIN_REQUEST_URL + "/" + username + "/park", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("ParkRequest",username + "parked !!");
            }
        },null);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Parkingid", parkingId);
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
