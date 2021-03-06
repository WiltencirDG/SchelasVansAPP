package com.schelas.schelasvans.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Veiculos;

public class DetailVeiculo extends AppCompatActivity {

    private static final String TAG = "SchelasVans";
    private TextView tvName;
    private TextView tvPlaca;
    private TextView tvModelo;
    private TextView tvCapacidade;
    private ImageView ivToolbar;
    private Veiculos veiculo;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_veiculo);

        veiculo = (Veiculos) getIntent().getSerializableExtra("veiculo");
        setUI();
        setData(veiculo);
        setToolbar();
    }

    private void setUI(){
        tvName = (TextView) findViewById(R.id.tv_nomeVeiculo);
        tvPlaca = (TextView) findViewById(R.id.tv_placaVeiculo);
        tvModelo = (TextView) findViewById(R.id.tv_modeloVeiculo);
        tvCapacidade = (TextView) findViewById(R.id.tv_capacidadeVeiculoD);
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

    private void setData(Veiculos veiculo){
        tvName.setText(veiculo.getDesc());
        tvPlaca.setText("Placa: "+veiculo.getPlaca());
        tvModelo.setText("Modelo: "+veiculo.getModelo());
        tvCapacidade.setText("Capacidade: "+veiculo.getCapacidade().toString());
    }

}
