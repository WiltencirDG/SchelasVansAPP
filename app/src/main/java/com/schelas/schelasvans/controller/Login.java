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
import com.schelas.schelasvans.model.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPass;
    private Button btLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setUI();
        Act();
    }

    private void Act(){
        final Validator validator = new Validator();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etEmail.getText().toString();
                final String password = etPass.getText().toString();

                if (validator.validateLogin(username, password)) {

//                    Response.Listener<String> responseListener = new Response.Listener<String>() {
//
//                        @Override
//                        public void onResponse(String response) {
//
//                            try {
//                                JSONObject jsonResponse = new JSONObject(response);
//                                boolean success = jsonResponse.getBoolean("success");
//
//                                if (success) {
//                                    String username = jsonResponse.getString("username");
//
//                                    Intent intent = new Intent(Login.this, Dashboard.class);
//                                    intent.putExtra("username", username);
//                                    Login.this.startActivity(intent);
//
//
//                                } else {
//                                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
//                                    builder.setMessage(R.string.loginFail)
//                                            .setNegativeButton(R.string.tryAgain, null)
//                                            .create()
//                                            .show();
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    };
//
//                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
//                    RequestQueue queue = Volley.newRequestQueue(Login.this);
//                    queue.add(loginRequest);

                    Intent intent = new Intent(Login.this, Dashboard.class);
                    intent.putExtra("username", username);
                    Login.this.startActivity(intent);

                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                    builder.setMessage(R.string.loginFailInval)
                            .setNegativeButton(R.string.tryAgain, null)
                            .create()
                            .show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent register = new Intent(Login.this, Registration.class);
                startActivity(register);
            }

        });

    }

    private void setUI(){
        btLogin = (Button) findViewById(R.id.btLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
    }

}