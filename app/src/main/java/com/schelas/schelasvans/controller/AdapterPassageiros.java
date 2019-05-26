package com.schelas.schelasvans.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.PassageiroRequest;
import com.schelas.schelasvans.model.Passageiros;

import java.util.List;


public class AdapterPassageiros extends RecyclerView.Adapter<AdapterPassageiros.ViewHolder>
        implements View.OnClickListener{

    private List<Passageiros> mData;
    private View.OnClickListener listener;
    private ImageView edit;
    private ImageView delete;
    static Context context;
    public AdapterPassageiros(List<Passageiros> myData) {
        mData = myData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;
        public TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.imgPass);
            tvName = v.findViewById(R.id.namePass);
            context = v.getContext();
        }
    }

    @Override
    public AdapterPassageiros.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_passageiros, parent, false);

        edit = view.findViewById(R.id.btnEdit);
        delete = view.findViewById(R.id.btnDel);

        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvName.setText(mData.get(position).getName());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(context, CadastroPassageiro.class);
                register.putExtra("id",mData.get(position).getIdPass());
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

                                Intent intent = new Intent(context, ListPassageiro.class);
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

                PassageiroRequest passageiroRequest = new PassageiroRequest(mData.get(position).getIdPass(),"del", responseListener);
                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(passageiroRequest);


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