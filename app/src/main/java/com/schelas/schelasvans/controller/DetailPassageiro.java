package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Passageiros;

public class DetailPassageiro extends AppCompatActivity {

    private ImageView ivImage;
    private TextView tvName;
    private TextView tvAddress;
    private TextView tvPhone;
    private TextView tvEmail;
    private Button btnMap;
    private ImageView ivToolbar;
    private Passageiros passageiro;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_passageiro);

        passageiro = (Passageiros) getIntent().getSerializableExtra("passageiro");
        setUI();
        setData(passageiro);
        setMap();
        setToolbar();
    }

    private void setUI(){
        ivImage = (ImageView) findViewById(R.id.iv_shop);
        tvName = (TextView) findViewById(R.id.tv_n_shop);
        tvAddress = (TextView) findViewById(R.id.tv_a_shop);
        tvPhone = (TextView) findViewById(R.id.tv_p_shop);
        tvEmail = (TextView) findViewById(R.id.tv_q_shop);
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

    private void setData(Passageiros passageiro){
        tvName.setText(passageiro.getName());
        tvAddress.setText("Endereço: "+passageiro.getAddress()+", "+passageiro.getAddressNumber());
        tvPhone.setText("Telefone: "+passageiro.getPhone());
        tvEmail.setText("Email: "+passageiro.getEmail());
    }

    private void setMap(){
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maps(passageiro);
            }
        });
    }

    private void Maps(Passageiros passageiro){
        Intent intent = new Intent(DetailPassageiro.this, Maps.class);
        intent.putExtra("passageiro", passageiro);
        startActivity(intent);
    }

}
