package com.schelas.schelasvans.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VeiculoRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://schelasvansapi.000webhostapp.com/api/post/Veiculo.php";
    private Map<String, String> params;

    public VeiculoRequest(String placa, String cor, String modelo, String marca, String capacidade, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("placa", placa);
        params.put("cor", cor);
        params.put("modelo", modelo);
        params.put("marca", marca);
        params.put("capacidade", capacidade);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
