package com.schelas.schelasvans.model;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class PassageiroRequest extends StringRequest {
    private Map<String, String> params;

    public PassageiroRequest(String ID, String nome, String email, String phone, String address, String number, String bairro, String cidade, String link, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/"+link+"/Passageiro.php", listener, null);
        params = new HashMap<>();
        params.put("id", ID);
        params.put("nome", nome);
        params.put("email", email);
        params.put("phone", phone);
        params.put("address", address);
        params.put("number", number);
        params.put("bairro", bairro);
        params.put("cidade", cidade);

    }

    public PassageiroRequest(String ID, String link, Response.Listener<String> listener){
        super(Method.POST, "https://schelasvansapi.000webhostapp.com/api/"+link+"/Passageiro.php", listener, null);
        params = new HashMap<>();
        params.put("id", ID);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
