package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.Passageiros;
import com.schelas.schelasvans.model.VeiculoRequest;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CadastroVeiculo extends AppCompatActivity {

    private EditText etplaca;
    private EditText etcor;
    private EditText etmodelo;
    private EditText etmarca;
    private EditText etcapacidade;
    private Button btncadastrar;
    private ImageView ivToolbar;
    private String link;

    private String type;
    private String veicId = "";

    static final String TAG = "Schelas Vans";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);
        setUI();
        setToolbar();
        Act();

        type = getIntent().getStringExtra("type");
        link = "post";

        if (type.contains("edit")) {
            veicId = getIntent().getStringExtra("id");
            doLoad();
            btncadastrar.setText(R.string.btnAtt);
            link = "put";
        }

    }

    private void setUI(){
        etplaca = findViewById(R.id.placa);
        etcor = findViewById(R.id.cor);
        etmodelo = findViewById(R.id.modelo);
        etmarca  = findViewById(R.id.marca);
        etcapacidade  = findViewById(R.id.capacidade);
        btncadastrar= findViewById(R.id.cadastro);
        ivToolbar = findViewById(R.id.imgNavBar);
    }

    private void Act(){
        final Validator validator = new Validator();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String placa = etplaca.getText().toString();
                final String cor = etcor.getText().toString();
                final String modelo = etmodelo.getText().toString();
                final String marca = etmarca.getText().toString();
                final String capacidade = etcapacidade.getText().toString();

                if(validator.validatePlaca(placa)){
                    if(validator.validateEmpty(marca)){
                        if(validator.validateEmpty(capacidade)) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Log.d(TAG, response);
                                        if (response.contains("true")) {

                                            Intent intent = new Intent(CadastroVeiculo.this, ListVeiculo.class);
                                            CadastroVeiculo.this.startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroVeiculo.this);
                                            builder.setMessage(R.string.cadastroFail)
                                                    .setNegativeButton(R.string.tryAgain, null)
                                                    .create()
                                                    .show();
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            };

                            VeiculoRequest veiculoRequest = new VeiculoRequest(veicId, placa, cor, modelo, marca, capacidade, link, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(CadastroVeiculo.this);
                            queue.add(veiculoRequest);
                        }else{
                            etcapacidade.setError("Obrigatório informar a capacidade do veiculo");
                        }
                    }else{
                        etmarca.setError("Informe a marca do veiculo");
                    }
                }else{
                    etplaca.setError("Placa vazia ou já cadastrada");
                }

            }
        });
    }

    private void setToolbar(){
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void doLoad() {
        final List<Veiculos> veics = new ArrayList<>();
        final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Veiculo.php?id=" + veicId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Veiculos veic = new Veiculos(ob.getInt("VeiculoId"),ob.getString("VeiculoPlaca"),ob.getString("VeiculoCor"),ob.getString("VeiculoModelo"),ob.getString("VeiculoMarca"),ob.getInt("VeiculoCapacidade"));
                        veics.add(veic);

                        etplaca.setText(veic.getPlaca());
                        etcor.setText(veic.getCor());
                        etmodelo.setText(veic.getModelo());
                        etmarca.setText(veic.getMarca());
                        etcapacidade.setText(veic.getCapacidade());

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

        RequestQueue requestQueue = Volley.newRequestQueue(CadastroVeiculo.this);
        requestQueue.add(stringRequest);

    }

}
