package com.schelas.schelasvans.model;

import android.util.ArrayMap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChecklistRequest extends StringRequest {
    private Map<String, String> params;

    public ChecklistRequest(String VeiculoId, List<Passageiros> Passes, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/put/Checklist.php", listener, null);
        params = new HashMap<>();

        Map<String,Boolean> mapPasses = new ArrayMap<>();

        for(Passageiros p : Passes){
            mapPasses.put(p.getIdPass(),p.getSelected());
        }

        params.put("VeiculoId", VeiculoId);
        params.put("Passageiros", mapPasses.toString());

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
