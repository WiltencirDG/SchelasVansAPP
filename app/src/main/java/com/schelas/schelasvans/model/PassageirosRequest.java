package com.schelas.schelasvans.model;


import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PassageirosRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://demo2749685.mockable.io/passageiros";
    private Map<String, String> params;

    public PassageirosRequest(String idPass, String nomePass, Response.Listener<String> listener){
        super(Method.GET, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("idPass", idPass);
        params.put("nomePass", nomePass);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}