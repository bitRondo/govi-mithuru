package com.example.govimithuruapp.core;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.govimithuruapp.accountManagement.AuthController;
import com.example.govimithuruapp.claimManagement.ClaimManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BackendManager {

    public static class ActionCodes {
        public static final int ONLY_CHECKING = 100;
        public static final int LOGIN_STEP_1 = 101;
        public static final int SUBMIT_CLAIM = 102;
        public static final int SUBMIT_EVIDENCE = 103;
        public static final int BUFFER_MONITOR = 104;
    }

    private static final String GOOGLE_CHECK_URL = "https://clients3.google.com/generate_204";
    private static final String BACKEND_URL = "https://govimithuru-backend.herokuapp.com/";
    public static final String CLAIM_SUFFIX = "claims/";
    public static final String EVIDENCE_SUFFIX = "evidences/";

    private RequestQueue requestQueue;
    private RequestBuffer buffer;
    private int statusCode = 0;
    private int errorCode = 0;

    private Context ctx;

    private ArrayList<ConnectivityMonitor> monitors;

    // Singleton
    private static BackendManager instance;

    private BackendManager() {
        buffer = new RequestBuffer();
        monitors = new ArrayList<>();
    }

    public static synchronized BackendManager getInstance(Context context) {
        if (instance == null) instance = new BackendManager();
        instance.ctx = context;
        return instance;
    }

    private ConnectivityMonitor getMonitor() {
        ConnectivityMonitor monitor = new ConnectivityMonitor(ctx.getApplicationContext());
        monitors.add(monitor);
        return monitor;
    }

    private void setConnectivityInMonitor(boolean c) {
        Iterator<ConnectivityMonitor> monitorIterator = monitors.iterator();
        while (monitorIterator.hasNext()) {
            ConnectivityMonitor m = monitorIterator.next();
            if (m.isAlive()) m.setConnected(c);
            else monitorIterator.remove();
        }
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

    private <T> void addToRequestBuffer(Request<T> request) {
        buffer.addToBuffer(request);
        getMonitor().start();
    }

    protected <T> void verifyInternetConnectivity(Request<T> request, int actionCode) {
        if (NetworkConnectionManager.getInstance(ctx.getApplicationContext()).isNetworkAvailable()) {
            StringRequest testRequest = new StringRequest(Request.Method.GET, GOOGLE_CHECK_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            showToast("Connected!");
                            if (actionCode == ActionCodes.BUFFER_MONITOR) {
                                setConnectivityInMonitor(true);
                                ArrayList<Request> bufferedRequests = buffer.fetchFromBuffer();
                                System.out.println("Dispatched " + bufferedRequests.size() + " Requests");
                                NotificationController.getInstance(ctx).removeNotification();
                                for (Request br : bufferedRequests) addToRequestQueue(br);
                            } else addToRequestQueue(request);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            if (volleyError instanceof TimeoutError || volleyError instanceof NoConnectionError) {
                                showToast("No Connection Available!");
                                switch (actionCode) {
                                    case ActionCodes.LOGIN_STEP_1:
                                        AuthController.getInstance().completeLoginStep1(null, ctx, false);
                                        break;
                                    case ActionCodes.BUFFER_MONITOR:
                                        setConnectivityInMonitor(false);
                                        break;
//                                    case ActionCodes.ONLY_CHECKING:
                                    case ActionCodes.SUBMIT_CLAIM:
                                    case ActionCodes.SUBMIT_EVIDENCE:
                                        addToRequestBuffer(request);
                                        NotificationController.getInstance(ctx).createNotification();
                                        break;
                                    default:
                                        System.out.println("Not Connected");
                                        break;
                                }
                            } else if (volleyError instanceof AuthFailureError) {
                                showToast("Authentication Error!");
                            } else if (volleyError instanceof ServerError) {
                                showToast("Server Error!");
                            } else if (volleyError instanceof NetworkError) {
                                showToast("Network Error!");
                            } else if (volleyError instanceof ParseError) {
                                showToast("Parse Error!");
                            }
                        }
                    }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    statusCode = response.statusCode;
                    return super.parseNetworkResponse(response);
                }
            };
            addToRequestQueue(testRequest);
        } else {
            switch (actionCode) {
                case ActionCodes.LOGIN_STEP_1:
                    AuthController.getInstance().completeLoginStep1(null, ctx, false);
                    break;
                case ActionCodes.SUBMIT_CLAIM:
                case ActionCodes.SUBMIT_EVIDENCE:
                    addToRequestBuffer(request);
                    NotificationController.getInstance(ctx).createNotification();
                    break;
                default:
                    System.out.println("Turned Off");
                    break;
            }
            showToast("Internet Connection is Turned Off!");

        }
    }

    public void postTextData(String suffix, HashMap<String, String> data, int actionCode) {
        String url = BACKEND_URL + suffix;
        System.out.println(url);

        JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Text POST Success!");
                        try {
                            if (actionCode == ActionCodes.SUBMIT_CLAIM) {
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
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        verifyInternetConnectivity(request, actionCode);
    }

    public void postImageData(String suffix, byte[] image, HashMap<String, String> textData, int actionCode) {
        String url = BACKEND_URL + suffix;
        System.out.println(url);

        CustomMultipartRequest request = new CustomMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        System.out.println("Image POST Success!");
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            System.out.println(obj.getString("evidenceID"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Error!");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return textData;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("image", new DataPart(String.format("%s.jpg", textData.get("evidenceID")), image));
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        verifyInternetConnectivity(request, actionCode);
    }

    public void getData(String suffix, int actionCode) {
        String url = BACKEND_URL + suffix;
        System.out.println(url);

        Request request;

        if (actionCode == ActionCodes.ONLY_CHECKING) {
            request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("CHECKING success!");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error!");
                            System.out.println(error.getMessage());
                        }
                    });

        } else {
            request = new JsonObjectRequest(url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("GET success!");
                            if (actionCode == ActionCodes.LOGIN_STEP_1)
                                AuthController.getInstance().completeLoginStep1(response, ctx, true);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("Error!");
                            System.out.println(error.getMessage());
                            if (error.networkResponse.statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                                if (actionCode == ActionCodes.LOGIN_STEP_1)
                                    AuthController.getInstance().completeLoginStep1(null, ctx, false);
                            }
                        }
                    });

        }
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        verifyInternetConnectivity(request, actionCode);
    }

    private void showToast(String text) {
        if (ctx instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) ctx;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
