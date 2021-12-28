package com.joaomarcos.beautysalon.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.ArrayList;

public class ListEmpresa extends RecyclerView.Adapter<ListEmpresa.MyViewHolder> {
    Context context;
    ArrayList<Empresas> moradorDeRuaEmpresas;
    Empresas empresas;


    @NonNull
    @Override
    public ListEmpresa.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListEmpresa.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
