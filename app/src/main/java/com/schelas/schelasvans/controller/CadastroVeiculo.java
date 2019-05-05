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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.VeiculoRequest;

public class CadastroVeiculo extends AppCompatActivity {

    private EditText etplaca;
    private EditText etcor;
    private EditText etmodelo;
    private EditText etmarca;
    private EditText etcapacidade;
    private Button btncadastrar;
    private ImageView ivToolbar;
    static final String TAG = "Schelas Vans";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_veiculo);
        setUI();
        setToolbar();
        Act();
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

                                            Intent intent = new Intent(CadastroVeiculo.this, ListPassageiro.class);
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

                            VeiculoRequest veiculoRequest = new VeiculoRequest(placa, cor, modelo, marca, capacidade, responseListener);
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

}
