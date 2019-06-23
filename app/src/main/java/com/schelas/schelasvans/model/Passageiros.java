package com.schelas.schelasvans.model;

import android.content.Context;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.controller.ChecklistDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Passageiros implements Serializable {

    private String idPass;
    private String name;
    private String Address;
    private String AddressNumber;
    private String Phone;
    private String email;
    private String bairro;
    private String cidade;

    public Passageiros(){

    }

    public Passageiros(String name, String idPass, String Address, String AddressNumber, String Phone, String email, String bairro, String cidade ){
        setName(name);
        setIdPass(idPass);
        setPhone(Phone);
        setAddress(Address);
        setAddressNumber(AddressNumber);
        setEmail(email);
        setBairro(bairro);
        setCidade(cidade);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdPass() {
        return idPass;
    }

    public void setIdPass(String idPass) {
        this.idPass = idPass;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPhone() {
        return Phone;
    }

    public void setAddressNumber(String addressNumber) {
        AddressNumber = addressNumber;
    }

    public String getAddressNumber() {
        return AddressNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Passageiros getById(Context context, String id) {
        final Passageiros[] pass = {new Passageiros()};

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://schelasvansapi.000webhostapp.com/api/get/Passageiro.php?id="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        pass[0] = new Passageiros(ob.getString("PassageiroId"), ob.getString("PassageiroNome"), ob.getString("PassageiroEmail"), ob.getString("PassageiroFone"), ob.getString("PassageiroLogradouro"), ob.getString("PassageiroNum"),ob.getString("PassageiroBairro"),ob.getString("PassageiroCidade"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        return pass[0];

    }

}