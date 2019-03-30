package com.schelas.schelasvans.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.schelas.schelasvans.R;
import com.schelas.schelasvans.model.Passageiros;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
        implements View.OnClickListener{

    private List<Passageiros> mData;
    private View.OnClickListener listener;

    public Adapter(List<Passageiros> myData) {
        mData = myData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivImage;
        public TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ivImage = v.findViewById(R.id.imgPass);
            tvName = v.findViewById(R.id.namePass);
        }
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.holder, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.ivImage.setImageResource(mData.get(position).getIdImage());
        holder.tvName.setText(mData.get(position).getName());
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