package com.schelas.schelasvans.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.Passageiros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CadastroPassageiro extends AppCompatActivity {

    private String ID;
    private EditText etnome;
    private EditText etemail;
    private EditText etphone;
    private EditText etaddress;
    private EditText etnumber;
    private EditText etbairro;
    private EditText etcidade;
    private Button btncadastrar;
    private ImageView ivToolbar;
    private String link;

    private String type;
    private String passId = "";
    static final String TAG = "Schelas Vans";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_passageiro);
        setUI();
        setToolbar();
        Act();

        type = getIntent().getStringExtra("type");
        link = "post";

        if (type.contains("edit")) {
            passId = getIntent().getStringExtra("id");
            doLoad();
            btncadastrar.setText(R.string.btnAtt);
            link = "put";
        }

    }

    private void setUI() {
        etnome = findViewById(R.id.etNome);
        etemail = findViewById(R.id.etEmail);
        etphone = findViewById(R.id.etPhone);
        etaddress = findViewById(R.id.etAddress);
        etnumber = findViewById(R.id.etAddressNum);
        etbairro = findViewById(R.id.etAddressBairro);
        etcidade = findViewById(R.id.etAddressCidade);
        btncadastrar = findViewById(R.id.btnCadPass);
        ivToolbar = findViewById(R.id.imgNavBar);
    }

    private void Act() {
        final Validator validator = new Validator();

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nome = etnome.getText().toString();
                final String email = etemail.getText().toString();
                final String phone = etphone.getText().toString();
                final String address = etaddress.getText().toString();
                final String number = etnumber.getText().toString();
                final String bairro = etbairro.getText().toString();
                final String cidade = etcidade.getText().toString();

                if (validator.validateEmail(email)) {

                    if (validator.validateNome(nome)) {

                        if (validator.validatePhone(phone)) {

                            if (validator.validateAddress(address)) {

                                if (validator.validateNumber(number)) {

                                    if (validator.validateBairro(bairro)) {

                                       if (validator.validateCidade(cidade)) {
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {

                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        Log.d(TAG, response);
                                                        if (response.contains("true")) {

                                                            Intent intent = new Intent(CadastroPassageiro.this, ListPassageiro.class);
                                                            CadastroPassageiro.this.startActivity(intent);

                                                        } else {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(CadastroPassageiro.this);
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

                                            PassageiroRequest passageiroRequest = new PassageiroRequest(passId, nome, email, phone, address, number, bairro, cidade, link, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(CadastroPassageiro.this);
                                            queue.add(passageiroRequest);


                                        } else {
                                            etcidade.setError("Campo inválido");
                                        }

                                    } else {
                                        etbairro.setError("Campo inválido");
                                    }

                                } else {
                                    etnumber.setError("Campo inválido");
                                }

                            } else {
                                etaddress.setError("Campo inválido");
                            }

                        } else {
                            etphone.setError("Campo inválido");
                        }

                    } else {
                        etnome.setError("Campo inválido");
                    }

                } else {
                    etemail.setError("Campo inválido");
                }

            }
        });
    }

    private void setToolbar() {
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void doLoad() {
        final List<Passageiros> passes = new ArrayList<>();
        final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Passageiro.php?id=" + passId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Passageiros p = new Passageiros(ob.getString("PassageiroNome"), ob.getString("PassageiroId"), ob.getString("PassageiroLogradouro"), ob.getString("PassageiroNum"), ob.getString("PassageiroFone"), ob.getString("PassageiroEmail"), ob.getString("PassageiroBairro"), ob.getString("PassageiroCidade"));
                        passes.add(p);

                        etnome.setText(p.getName());
                        etemail.setText(p.getEmail());
                        etphone.setText(p.getPhone());
                        etaddress.setText(p.getAddress());
                        etnumber.setText(p.getAddressNumber());
                        etbairro.setText(p.getBairro());
                        etcidade.setText(p.getCidade());


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

        RequestQueue requestQueue = Volley.newRequestQueue(CadastroPassageiro.this);
        requestQueue.add(stringRequest);

    }

}
