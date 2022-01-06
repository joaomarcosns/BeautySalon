package com.joaomarcos.beautysalon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.ArrayList;

public class ListMaquiagem extends RecyclerView.Adapter<ListMaquiagem.MyViewHolder> {
    Context context;
    ArrayList<Empresas> empresasArrayList;
    Empresas empresas;

    public ListMaquiagem(Context context, ArrayList<Empresas> empresasArrayList) {
        this.context = context;
        this.empresasArrayList = empresasArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_list_favoritos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Empresas emp = empresasArrayList.get(position);

        holder.nome_loja.setText(emp.getNomeEmpresa());
        holder.tipo_categoria.setText(emp.getCategoriaPricipal());
        holder.descricao.setText(emp.getDescricao());
    }

    @Override
    public int getItemCount() {
        return empresasArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nome_loja, tipo_categoria, descricao;
        private FloatingActionButton btnWhatsapp, btnLove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_loja = itemView.findViewById(R.id.nome_loja);
            tipo_categoria = itemView.findViewById(R.id.tipo_categoria);
            descricao = itemView.findViewById(R.id.descricao);
            btnWhatsapp = itemView.findViewById(R.id.btnWhatsapp);
            btnLove = itemView.findViewById(R.id.btnLove);
        }
    }
}
