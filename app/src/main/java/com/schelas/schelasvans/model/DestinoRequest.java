package com.schelas.schelasvans.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DestinoRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://schelasvansapi.000webhostapp.com/api/post/Destino.php";
    private Map<String, String> params;

    public DestinoRequest(String Id, String descricao, String rua, String number, String bairro, String cidade, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id",Id);
        params.put("desc",descricao);
        params.put("rua", rua);
        params.put("number", number);
        params.put("bairro", bairro);
        params.put("cidade", cidade);
    }

    public DestinoRequest(String ID, String link, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/"+link+"/Destino.php", listener, null);
        params = new HashMap<>();
        params.put("id", ID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
