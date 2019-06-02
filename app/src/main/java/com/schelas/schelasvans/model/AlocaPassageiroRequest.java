package com.schelas.schelasvans.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlocaPassageiroRequest extends StringRequest {
    private Map<String, String> params;

    public AlocaPassageiroRequest(List<String> passes, String veic, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/Aloca/PassVeic.php", listener, null);
        params = new HashMap<>();
        params.put("emails", passes.toString());
        params.put("placa", veic);
    }

    @Override
    public Map<String,String> getParams() {
        return params;
    }
}
