package com.schelas.schelasvans.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.schelas.schelasvans.R;

public class Alocar extends AppCompatActivity {


    private Button btnAlocar;
    private ImageView ivToolbar;
    private Spinner spVeic;
    private Spinner spPass;
    private Spinner spDest;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alocar);

        setUI();
        setData();
        setToolbar();
    }

    private void setUI(){
        spVeic = findViewById(R.id.spinner1);
        spPass = findViewById(R.id.spinner2);
        spDest = findViewById(R.id.spinner3);
    }

    private void setToolbar(){
        ivToolbar = (ImageView) findViewById(R.id.imgNavBar);

        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setData(){

    }

}
