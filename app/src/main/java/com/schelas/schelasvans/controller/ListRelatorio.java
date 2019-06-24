package com.schelas.schelasvans.controller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Destinos;
import com.schelas.schelasvans.model.Passageiros;
import com.schelas.schelasvans.model.Veiculos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListRelatorio extends AppCompatActivity {
    private static final String URL_FETCH = "https://schelasvansapi.000webhostapp.com/api/get/Passageiro.php";
    private static final String TAG = "SchelasVans";
    private RecyclerView rvRelatorios;
    private ImageView ivToolbar;
    private Toolbar toolbar;
    private List<String> relatorios;
    private AdapterRelatorios mAdapter;
    private Boolean ready = false;
    private List<Passageiros> passageiros;
    private List<Veiculos> veiculos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_relatorio);
        setUI();
        setToolbar();
        relatorios = getRelatorios();
        setRecyclerView();
        getVeiculos();
        getPassageiroDestino();
        Toast.makeText(ListRelatorio.this, "Carregando dados...", Toast.LENGTH_SHORT).show();
    }

    private void setUI(){
        toolbar = findViewById(R.id.destool);
        rvRelatorios = findViewById(R.id.rv_relatorio);
    }

    private void setRecyclerView(){

        rvRelatorios.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvRelatorios.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterRelatorios(relatorios);
        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ready){
                    goToDescription(relatorios.get(rvRelatorios.getChildLayoutPosition(view)));
                }else{
                    Toast.makeText(ListRelatorio.this, "Dados ainda em carregamento...", Toast.LENGTH_SHORT).show();
                }


            }
        });

        rvRelatorios.setAdapter(mAdapter);
        rvRelatorios.setItemAnimator(new DefaultItemAnimator());


    }

    private void goToDescription(String relatorio){

        if(relatorio.equals("Passageiros")){
            createPdfPassageiro();
        } else if(relatorio.equals("Veiculos")){
            createPdfVeiculo();
        }

    }

    private List<String> getRelatorios(){
        final List<String> relats = new ArrayList<>();

        relats.add("Passageiros");
        relats.add("Veiculos");

        return relats;
    }

    private void setToolbar(){
        ivToolbar = findViewById(R.id.imgNavBar);
        ivToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void createPdfPassageiro(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHeight = (int) height, convertWidth = (int) width;

        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(400, convertHeight, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint normalText = new Paint();
        normalText.setColor(Color.BLACK);
        normalText.setTextSize(12);

        Paint titleText = new Paint();
        titleText.setColor(Color.BLACK);
        titleText.setTextSize(14);
        titleText.setFakeBoldText(true);


        canvas.drawText("Passageiros", 125, 30, titleText);

        int x = 30;
        int y = 70;
        int lineNumber = 1;
        for(Passageiros p : passageiros){
            canvas.drawText(String.valueOf(lineNumber), 10, y, normalText);
            canvas.drawText(p.getName(), x, y, normalText);
            canvas.drawText(p.getEmail(), x+150, y, normalText);
            y+=20;
            lineNumber++;
        }

        // finish the page
        document.finishPage(page);
        // write the document content
        String targetPdf = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Passageiros.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ocorreu um erro: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();


        //Open file
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = filePath.getName().substring(filePath.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.your.package.fileProvider", filePath);
                intent.setDataAndType(contentUri, type);
            } else {
                intent.setDataAndType(Uri.fromFile(filePath), type);
            }
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(getApplicationContext(), "Nenhuma aplicação encontrada para abrir esse documento.", Toast.LENGTH_LONG).show();
        }

    }

    private void createPdfVeiculo(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float height = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHeight = (int) height, convertWidth = (int) width;

        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(400, convertHeight, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint normalText = new Paint();
        normalText.setColor(Color.BLACK);
        normalText.setTextSize(12);

        Paint titleText = new Paint();
        titleText.setColor(Color.BLACK);
        titleText.setTextSize(14);
        titleText.setFakeBoldText(true);


        canvas.drawText("Veiculos", 150, 30, titleText);

        int x = 30;
        int y = 70;
        int lineNumber = 1;
        for(Veiculos v : veiculos){
            canvas.drawText(String.valueOf(lineNumber), 10, y, normalText);
            canvas.drawText(v.getPlaca(), x, y, normalText);
            canvas.drawText(v.getModelo(), x+80, y, normalText);
            canvas.drawText(String.valueOf(v.getCapacidade()+" lugares"), x+160, y, normalText);
            y+=20;
            lineNumber++;
        }

        // finish the page
        document.finishPage(page);
        // write the document content
        String targetPdf = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Veiculos.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ocorreu um erro: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();


        //Open file
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = filePath.getName().substring(filePath.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.your.package.fileProvider", filePath);
                intent.setDataAndType(contentUri, type);
            } else {
                intent.setDataAndType(Uri.fromFile(filePath), type);
            }
            startActivity(intent);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(getApplicationContext(), "Nenhuma aplicação encontrada para abrir esse documento.", Toast.LENGTH_LONG).show();
        }

    }

    public void getPassageiroDestino(){

        final List<Passageiros> passes = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_FETCH , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Passageiros pass = new Passageiros(ob.getString("PassageiroNome"), ob.getString("PassageiroId"), ob.getString("PassageiroLogradouro"), ob.getString("PassageiroNum"), ob.getString("PassageiroFone"), ob.getString("PassageiroEmail"), ob.getString("PassageiroBairro"), ob.getString("PassageiroCidade"));
                        passes.add(pass);
                    }
                    Toast.makeText(ListRelatorio.this, "Dados carregados", Toast.LENGTH_SHORT).show();
                    passageiros = passes;
                    ready = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(ListRelatorio.this);
        requestQueue.add(stringRequest);


    }

    private void getVeiculos(){
        final List<Veiculos> veics = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://schelasvansapi.000webhostapp.com/api/get/Veiculo.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jobj = new JSONArray(response);

                    for (int i = 0; i < jobj.length(); i++) {
                        JSONObject ob = jobj.getJSONObject(i);
                        Veiculos veic = new Veiculos(ob.getInt("VeiculoId"),ob.getString("VeiculoPlaca"),ob.getString("VeiculoCor"),ob.getString("VeiculoModelo"),ob.getString("VeiculoMarca"),ob.getInt("VeiculoCapacidade"));
                        veics.add(veic);

                    }
                    veiculos = veics;
                    ready = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(ListRelatorio.this);
        requestQueue.add(stringRequest);

    }


}

