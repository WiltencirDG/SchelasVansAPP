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

public class CadastroVeiculo extends AppCompatActivity {

    private EditText etnome;
    private EditText etemail;
    private EditText etphone;
    private EditText etaddress;
    private EditText etnumber;
    private EditText etbairro;
    private EditText etcidade;
    private Button btncadastrar;
    private ImageView ivToolbar;
    static final String TAG = "Schelas Vans";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_passageiro);
        setUI();
        setToolbar();
        Act();
    }

    private void setUI(){
        etnome = findViewById(R.id.etNome);
        etemail= findViewById(R.id.etEmail);
        etphone = findViewById(R.id.etPhone);
        etaddress  = findViewById(R.id.etAddress);
        etnumber  = findViewById(R.id.etAddressNum);
        etbairro = findViewById(R.id.etAddressBairro);
        etcidade= findViewById(R.id.etAddressCidade);
        btncadastrar= findViewById(R.id.btnCadPass);
        ivToolbar = findViewById(R.id.imgNavBar);
    }

    private void Act(){
        final Validator validator = new Validator();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = etnome.getText().toString();
                final String email = etemail.getText().toString();
                final String phone= etphone.getText().toString();
                final String address= etaddress.getText().toString();
                final String number = etnumber.getText().toString();
                final String bairro = etbairro.getText().toString();
                final String cidade = etcidade.getText().toString();

                if(validator.validateEmail(email)){

                    if(validator.validateNome(nome)){

                        if(validator.validatePhone(phone)){

                            if(validator.validateAddress(address)){

                                if(validator.validateNumber(number)){

                                    if(validator.validateBairro(bairro)){

                                        if(validator.validateCidade(cidade)){

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

                                            PassageiroRequest passageiroRequest = new PassageiroRequest(nome, email, phone, address, number, bairro, cidade, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(CadastroVeiculo.this);
                                            queue.add(passageiroRequest);



                                        }else{
                                            etcidade.setError("Campo inválido");
                                        }

                                    }else{
                                        etbairro.setError("Campo inválido");
                                    }

                                }else{
                                    etnumber.setError("Campo inválido");
                                }

                            }else{
                                etaddress.setError("Campo inválido");
                            }

                        }else{
                            etphone.setError("Campo inválido");
                        }

                    }else{
                        etnome.setError("Campo inválido");
                    }

                }else{
                    etemail.setError("Campo inválido");
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
