package com.schelas.schelasvans.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class VeiculoRequest extends StringRequest {
    private Map<String, String> params;

    public VeiculoRequest(String id, String placa, String cor, String modelo, String marca, String capacidade, String link, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/"+link+"/Veiculo.php", listener, null);
        params = new HashMap<>();
        params.put("id",id);
        params.put("placa", placa);
        params.put("cor", cor);
        params.put("modelo", modelo);
        params.put("marca", marca);
        params.put("capacidade", capacidade);
    }

    public VeiculoRequest(String ID, String link, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/"+link+"/Veiculo.php", listener, null);
        params = new HashMap<>();
        params.put("id", ID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
