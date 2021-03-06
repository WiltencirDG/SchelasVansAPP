package com.schelas.schelasvans.model;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.controller.Rotas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Veiculos implements Serializable {
    private Integer id;
    private String placa;
    private String cor;
    private String modelo;
    private String marca;
    private Integer capacidade;
    private String Desc;


    public Veiculos(Integer id, String placa, String cor, String modelo, String marca, Integer capacidade) {
        setId(id);
        setPlaca(placa);
        setCor(cor);
        setModelo(modelo);
        setMarca(marca);
        setCapacidade(capacidade);
        this.Desc = "Veiculo";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public String getDesc() { return this.Desc; }

}
