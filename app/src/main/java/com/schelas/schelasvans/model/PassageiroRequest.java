package com.schelas.schelasvans.model;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PassageiroRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://schelasvansapi.000webhostapp.com/api/post/Passageiro.php";
    private Map<String, String> params;

    public PassageiroRequest(String nome, String email, String phone, String address, String number, String bairro, String cidade, Response.Listener<String> listener){
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nome", nome);
        params.put("email", email);
        params.put("phone", phone);
        params.put("address", address);
        params.put("number", number);
        params.put("bairro", bairro);
        params.put("cidade", cidade);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
