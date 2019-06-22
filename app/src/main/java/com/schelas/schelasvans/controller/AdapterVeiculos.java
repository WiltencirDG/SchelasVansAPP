package com.schelas.schelasvans.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.VeiculoRequest;
import com.schelas.schelasvans.model.Veiculos;

import java.util.List;


public class AdapterVeiculos extends RecyclerView.Adapter<AdapterVeiculos.ViewHolder>
        implements View.OnClickListener{

    private List<Veiculos> mData;
    private View.OnClickListener listener;
    private ImageView edit;
    private ImageView delete;
    static Context context;

    public AdapterVeiculos(List<Veiculos> myData) {
        mData = myData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;
        public TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.imgVei);
            tvName = v.findViewById(R.id.nameVei);
            context = v.getContext();
        }
    }

    @Override
    public AdapterVeiculos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_veiculo, parent, false);

        edit = view.findViewById(R.id.btnEdit);
        delete = view.findViewById(R.id.btnDel);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(mData.get(position).getPlaca());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(context, CadastroVeiculo.class);
                register.putExtra("id",mData.get(position).getId().toString());
                register.putExtra("type","edit");
                context.startActivity(register);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            if (response.contains("true")) {

                                Intent intent = new Intent(context, ListVeiculo.class);
                                context.startActivity(intent);

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

                VeiculoRequest veiculoRequest = new VeiculoRequest(mData.get(position).getId().toString(),"del", responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(veiculoRequest);


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null)
            listener.onClick(v);
    }


}