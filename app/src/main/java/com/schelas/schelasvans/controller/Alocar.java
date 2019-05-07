package com.schelas.schelasvans.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.schelas.schelasvans.R;

public class Alocar extends AppCompatActivity {


    private Button btnAlocar;
    private ImageView ivToolbar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alocar);

        setUI();
        setData();
        setToolbar();
    }

    private void setUI(){

    }

    private void setToolbar(){
        ivToolbar = (ImageView) findViewById(R.id.imgNavBar);

        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void setData(){

    }

}
