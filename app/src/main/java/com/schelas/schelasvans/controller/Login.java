package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.main.MainActivity;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPass;
    private Button btLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setUI();
        Act();
    }

    private void Act(){
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                Login.this.startActivity(intent);
            }
        });
    }

    private void setUI(){
        btLogin = (Button) findViewById(R.id.btLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass  = (EditText) findViewById(R.id.etPass);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
    }

}
