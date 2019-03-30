package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.main.Dashboard;
import com.schelas.schelasvans.model.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration extends AppCompatActivity {

    private EditText etEmail;
    private EditText etUsuario;
    private EditText etSenha;
    private EditText etConfSenha;
    private Button btnRegistrar;
    private Button btnGoLogin;
    private TextView tvErrors;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setUI();
        Act();
    }

    private void setUI(){
        tvErrors = (TextView) findViewById(R.id.tvErrors);
        etEmail = (EditText) findViewById(R.id.rEmail);
        etUsuario = (EditText) findViewById(R.id.rUser);
        etSenha = (EditText) findViewById(R.id.rPass);
        etConfSenha = (EditText) findViewById(R.id.rConfPass);
        btnRegistrar = (Button) findViewById(R.id.rRegBtn);
        btnGoLogin = (Button) findViewById(R.id.rLoginBtn);
        tvErrors.setVisibility(View.INVISIBLE);
    }

    private void Act(){
        final Validator validator = new Validator();
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String username = etUsuario.getText().toString();
                final String password = etSenha.getText().toString();
                final String confsenha = etConfSenha.getText().toString();

                tvErrors.setVisibility(View.INVISIBLE);

                if (validator.validateDados(email, username, password, confsenha)) {
                    if (validator.validatePass(password, confsenha)) {
                        if (validator.validateEmail(email)) {
                            final Response.Listener<String> responseListener = new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    try {

                                        if (response.equals("true")) {

                                            Intent intent = new Intent(Registration.this, Login.class);
                                            Registration.this.startActivity(intent);

                                        } else {
                                            android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                            builder.setMessage(R.string.regFail)
                                                    .setNegativeButton(R.string.tryAgain, null)
                                                    .create()
                                                    .show();
                                        }


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            RegisterRequest registerRequest = new RegisterRequest(username, email, password, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(Registration.this);
                            queue.add(registerRequest);
                        }else{
                            etEmail.setError("Email inválido");
                        }
                    }else{
                        etConfSenha.setError("As senhas não são iguais");
                    }
                }else{
                    tvErrors.setVisibility(View.VISIBLE);
                    tvErrors.setText(R.string.errorGeneral);
                }
            }
        });

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                Registration.this.startActivity(intent);
            }
        });
    }

}