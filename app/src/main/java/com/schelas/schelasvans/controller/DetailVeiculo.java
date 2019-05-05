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
import com.schelas.schelasvans.model.Veiculos;

public class DetailVeiculo extends AppCompatActivity {

    private TextView tvName;
    private TextView tvPlaca;
    private TextView tvModelo;
    private TextView tvCapacidade;
    private ImageView ivToolbar;
    private Veiculos veiculo;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_destino);

        veiculo = (Veiculos) getIntent().getSerializableExtra("veiculo");
        setUI();
        setData(veiculo);
        setToolbar();
    }

    private void setUI(){
        tvName = (TextView) findViewById(R.id.tv_nomeVeiculo);
        tvPlaca = (TextView) findViewById(R.id.tv_placaVeiculo);
        tvModelo = (TextView) findViewById(R.id.tv_modeloVeiculo);
        tvCapacidade = (TextView) findViewById(R.id.tv_capacidadeVeiculo);
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

    private void setData(Veiculos veiculo){
        tvName.setText(veiculo.getDesc());
        tvPlaca.setText(veiculo.getPlaca());
        tvModelo.setText(veiculo.getModelo());
        tvCapacidade.setText(veiculo.getCapacidade());
    }

}
