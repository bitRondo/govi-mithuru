package com.example.govimithuruapp.core;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.govimithuruapp.claimManagement.ClaimManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BackendManager {

    private static final String BACKEND_URL = "https://govimithuru-backend.herokuapp.com/";
    public static final String CLAIM_SUFFIX = "claims/";

    private RequestQueue requestQueue;
    private static Context ctx;

    // Singleton
    private static BackendManager instance;
    private BackendManager(Context context) {
        ctx = context;
    }

    public static synchronized BackendManager getInstance(Context context) {
        if (instance == null) instance = new BackendManager(context);
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void postData(String suffix, HashMap<String, String> data, int actionCode) {
        String url = BACKEND_URL + suffix;
        System.out.println(url);

        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("POST Success!");
                        try {
                            if (actionCode == ClaimManager.SUBMIT_CLAIM) {
                                System.out.println(response.getString("claimID"));
                                ClaimManager.getInstance().saveClaimInUser(ctx.getApplicationContext());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Error!");
                System.out.println(error.networkResponse);
                System.out.println(error.getMessage());
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        addToRequestQueue(request);
    }

}
