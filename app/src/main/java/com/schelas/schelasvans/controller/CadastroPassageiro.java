package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.Passageiros;

public class CadastroPassageiro extends AppCompatActivity {

    private EditText etnome;
    private EditText etemail;
    private EditText etphone;
    private EditText etaddress;
    private EditText etnumber;
    private EditText etbairro;
    private EditText etcidade;
    private Button btncadastrar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_passageiro);
        setUI();
        Act();
    }

    private void setUI(){
        etnome = findViewById(R.id.etNome);
        etemail= findViewById(R.id.etEmail);
        etphone = findViewById(R.id.etPhone);
        etaddress  = findViewById(R.id.etAdress);
        etnumber  = findViewById(R.id.etAdressNum);
        etbairro = findViewById(R.id.etAdressBairro);
        etcidade= findViewById(R.id.etAdressCidade);
        btncadastrar= findViewById(R.id.btnCadPass);
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

                                                        if (response.contains("true")) {

                                                            Intent intent = new Intent(CadastroPassageiro.this, ListPassageiro.class);
                                                            CadastroPassageiro.this.startActivity(intent);

                                                        } else {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPassageiro.this);
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

                                            PassageiroRequest loginRequest = new PassageiroRequest(nome, email, phone, address, number, bairro, cidade, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(CadastroPassageiro.this);
                                            queue.add(loginRequest);



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

}
