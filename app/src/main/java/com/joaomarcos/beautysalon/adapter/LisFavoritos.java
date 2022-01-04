package com.joaomarcos.beautysalon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.clientes.LoveEmpresa;
import com.joaomarcos.beautysalon.objeto.Empresas;
import com.joaomarcos.beautysalon.objeto.Favoritos;

import java.util.ArrayList;
import java.util.List;

public class LisFavoritos extends RecyclerView.Adapter<LisFavoritos.MyViewHolder> {
    Context context;
    ArrayList<Favoritos> favoritosArrayList;
    ArrayList<Empresas> empresasArrayList = new ArrayList<Empresas>();
    Favoritos favoritos;

    public LisFavoritos(Context context, ArrayList<Favoritos> favoritosArrayList) {
        this.context = context;
        this.favoritosArrayList = favoritosArrayList;
    }

    @NonNull
    @Override
    public LisFavoritos.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_list_favoritos, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LisFavoritos.MyViewHolder holder, int position) {
        Favoritos fov = favoritosArrayList.get(position);

        System.out.println(empresasArrayList);

    }

    @Override
    public int getItemCount() {
        return favoritosArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
