package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Destinos;
import com.schelas.schelasvans.model.Passageiros;

public class DetailDestino extends AppCompatActivity {

    private TextView tvName;
    private TextView tvAddress;
    private TextView tvCidade;
    private Button btnMap;
    private ImageView ivToolbar;
    private Destinos destino;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destino);

        destino = (Destinos) getIntent().getSerializableExtra("destino");
        setUI();
        setData(destino);
        setMap();
        setToolbar();
    }

    private void setUI(){
        tvName = (TextView) findViewById(R.id.tv_nomeDestino);
        tvAddress = (TextView) findViewById(R.id.tv_addressDestino);
        tvCidade = (TextView) findViewById(R.id.tv_cityDestino);
        btnMap = (Button) findViewById(R.id.btn_map);
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

    private void setData(Destinos destino){
        tvName.setText(destino.getDescricao());
        tvAddress.setText(destino.getRua()+", "+destino.getNum());
        tvCidade.setText(destino.getCidade());
    }

    private void setMap(){
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maps(destino);
            }
        });
    }

    private void Maps(Destinos destino){
        Intent intent = new Intent(DetailDestino.this, Maps.class);
        intent.putExtra("destino", destino);
        startActivity(intent);
    }

}
