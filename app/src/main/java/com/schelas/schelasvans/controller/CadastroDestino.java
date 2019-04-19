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
import com.schelas.schelasvans.model.DestinoRequest;
import com.schelas.schelasvans.model.PassageiroRequest;

public class CadastroDestino extends AppCompatActivity {


    private EditText etRua;
    private EditText etNum;
    private EditText etBairro;
    private EditText etCidade;
    private Button btncadastrar;
    private ImageView ivToolbar;
    static final String TAG = "Schelas Vans";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_destino);
        setUI();
        setToolbar();
        Act();
    }

    private void setUI(){
        etRua = findViewById(R.id.rua);
        etNum = findViewById(R.id.numero);
        etBairro = findViewById(R.id.bairro);
        etCidade = findViewById(R.id.cidade);
        btncadastrar = findViewById(R.id.btnCadastroDest);
        ivToolbar = findViewById(R.id.imgNavBar);
    }

    private void Act(){
        final Validator validator = new Validator();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String rua = etRua.getText().toString();
                final String number = etNum.getText().toString();
                final String bairro = etBairro.getText().toString();
                final String cidade = etCidade.getText().toString();

                if(validator.validateEmpty(rua)){

                    if(validator.validateEmpty(bairro)) {

                        if (validator.validateEmpty(cidade)) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Log.d(TAG, response);
                                        if (response.contains("true")) {

                                            Intent intent = new Intent(CadastroDestino.this, ListDestino.class);
                                            CadastroDestino.this.startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroDestino.this);
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

                            DestinoRequest destinoRequest = new DestinoRequest(rua, number, bairro, cidade, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(CadastroDestino.this);
                            queue.add(destinoRequest);
                        } else {
                            etCidade.setError("Cidade inválida.");
                        }
                    }else{
                        etBairro.setError("Bairro inválido.");
                    }
                }else{
                    etRua.setError("Rua inválida.");
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
