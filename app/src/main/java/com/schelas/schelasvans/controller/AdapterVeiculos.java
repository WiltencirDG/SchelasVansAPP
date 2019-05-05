package com.schelas.schelasvans.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Veiculos;

import java.util.List;


public class AdapterVeiculos extends RecyclerView.Adapter<AdapterVeiculos.ViewHolder>
        implements View.OnClickListener{

    private List<Veiculos> mData;
    private View.OnClickListener listener;

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
        }
    }

    @Override
    public AdapterVeiculos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder_veiculo, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(mData.get(position).getPlaca());
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