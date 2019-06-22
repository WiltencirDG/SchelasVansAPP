package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.main.Dashboard;
import com.schelas.schelasvans.model.DestinoRequest;
import com.schelas.schelasvans.model.Destinos;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.Passageiros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CadastroDestino extends AppCompatActivity {

    private String ID;
    private EditText etDesc;
    private EditText etRua;
    private EditText etNum;
    private EditText etBairro;
    private Spinner spCidade;
    private Button btncadastrar;
    private ImageView ivToolbar;
    private List<String> cities;
    private String link;
    private String type;
    private String destId = "";
    static final String TAG = "Schelas Vans";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_destino);
        setUI();
        setData();
        setToolbar();
        Act();

        type = getIntent().getStringExtra("type");
        link = "post";

        if (type.contains("edit")) {
            destId = getIntent().getStringExtra("id");
            doLoad();
            btncadastrar.setText(R.string.btnAtt);
            link = "put";
        }

    }

    private void setUI(){
        etDesc = findViewById(R.id.descricao);
        etRua = findViewById(R.id.rua);
        etNum = findViewById(R.id.numero);
        etBairro = findViewById(R.id.bairro);
        spCidade = findViewById(R.id.spinnerCityDestino);
        btncadastrar = findViewById(R.id.btnCadastroDest);
        ivToolbar = findViewById(R.id.imgNavBar);
    }

    private void setData(){
        cities = getCities();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cities);
        spCidade.setAdapter(adapter);

    }

    private void Act(){
        final Validator validator = new Validator();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc = etDesc.getText().toString();
                final String rua = etRua.getText().toString();
                final String number = etNum.getText().toString();
                final String bairro = etBairro.getText().toString();
                final String cidade = spCidade.getSelectedItem().toString();

                if(validator.validateEmpty(rua) && validator.validateEmpty(desc)){

                    if(validator.validateEmpty(bairro)) {

                        if (validator.validateEmpty(cidade)) {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        Log.d(TAG, response);
                                        if (response.contains("true")) {

                                            Intent intent = new Intent(CadastroDestino.this, ListDestino.class);
                                            CadastroDestino.this.startActivity(intent);

                                        } else {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroDestino.this);
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

                            DestinoRequest destinoRequest = new DestinoRequest(destId,desc,rua, number, bairro, cidade, link, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(CadastroDestino.this);
                            queue.add(destinoRequest);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroDestino.this);
                            builder.setMessage(R.string.cadastroFail)
                                    .setNegativeButton(R.string.tryAgain, null)
                                    .create()
                                    .show();
                        }
                    }else{
                        etBairro.setError("Bairro inválido.");
                    }
                }else{
                    etRua.setError("Rua inválida.");
                }
            }
        });
    }

    private void setToolbar(){
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CadastroDestino.this, ListDestino.class);
                CadastroDestino.this.startActivity(intent);
            }
        });

    }

    private void doLoad() {
        final List<Destinos> dests = new ArrayList<>();
        final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Destino.php?id=" + destId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Destinos dest = new Destinos(ob.getInt("DestinoId"),ob.getString("DestinoDesc"),ob.getString("DestinoLogradouro"),ob.getString("DestinoNum"),ob.getString("DestinoBairro"),ob.getString("DestinoCidade"));
                        dests.add(dest);

                        etDesc.setText(dest.getDescricao());
                        etRua.setText(dest.getRua());
                        etNum.setText(dest.getNum());
                        etBairro.setText(dest.getBairro());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(CadastroDestino.this);
        requestQueue.add(stringRequest);

    }

    private List<String> getCities(){
        final List<String> cities = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://schelasvansapi.000webhostapp.com/api/get/Cidade.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        cities.add(ob.getString("CidadeNome"));
                    }
                    ((BaseAdapter) spCidade.getAdapter()).notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CadastroDestino.this, "Erro ao carregar as cidades.", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(CadastroDestino.this);
        requestQueue.add(stringRequest);

        return cities;
    }

}
