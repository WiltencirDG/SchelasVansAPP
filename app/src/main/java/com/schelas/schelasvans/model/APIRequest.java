package com.schelas.schelasvans.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class APIRequest extends StringRequest {
    private static final String REQUEST_URL = "https://schelasvansapi.000webhostapp.com/api/Check.php";
    private Map<String, String> params;

    public APIRequest(Response.Listener<String> listener){
        super(Method.POST, REQUEST_URL, listener, null);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}