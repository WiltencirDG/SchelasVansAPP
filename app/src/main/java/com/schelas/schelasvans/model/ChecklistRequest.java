package com.schelas.schelasvans.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChecklistRequest extends StringRequest {
    private Map<String, String> params;

    public ChecklistRequest(String VeiculoId, List<Passageiros> Passes, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/put/Checklist.php", listener, null);
        params = new HashMap<>();
        params.put("VeiculoId", VeiculoId);
        params.put("Passageiros", Passes.toString());

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
