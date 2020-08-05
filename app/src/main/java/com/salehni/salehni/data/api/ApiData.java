package com.salehni.salehni.data.api;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.salehni.salehni.R;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class ApiData {

    RequestQueue queue;

    String url = "";
    Context context;
    InterfaceApi interfaceApi;
    Map<String, String> headerParams;
    String bodyParams;

    //GET
    public void getdata(Context context, InterfaceApi interfaceApi, String url, Map<String, String> headerParams) {

        this.url = url;
        this.context = context;
        this.interfaceApi = interfaceApi;
        this.headerParams = headerParams;

        getRequestApi();

    }

    //POST
    public void getdata(Context context, InterfaceApi interfaceApi, String url, Map<String, String> headerParams, String bodyParams) {

        this.url = url;
        this.context = context;
        this.interfaceApi = interfaceApi;
        this.headerParams = headerParams;
        this.bodyParams = bodyParams;

        postRequestApi();
    }

    private void getRequestApi() {


        queue = Volley.newRequestQueue(context);

//        String url = Constant.mainURLApi;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        interfaceApi.callbackOnSuccess(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        interfaceApi.callbackOnError(context.getResources().getString(R.string.servererror));
                    }
                }
        );
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

    }

    private void postRequestApi() {

        queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        interfaceApi.callbackOnSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        interfaceApi.callbackOnError(context.getResources().getString(R.string.servererror));

                    }
                }
        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return bodyParams == null ? null : bodyParams.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerParams;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                120000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);
    }

}
