package com.joaomarcos.beautysalon.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.clientes.HomeCliente;
import com.joaomarcos.beautysalon.objeto.Empresas;
import com.joaomarcos.beautysalon.objeto.Favoritos;

import java.util.ArrayList;
import java.util.Objects;

public class ListEmpresa extends RecyclerView.Adapter<ListEmpresa.MyViewHolder> {

    Context context;
    ArrayList<Empresas> empresasArrayList;
    Empresas empresas;

    public ListEmpresa(Context context, ArrayList<Empresas> empresasArrayList) {
        this.context = context;
        this.empresasArrayList = empresasArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_list_empresa, parent, false);
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


            btnWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    empresas = empresasArrayList.get(getAdapterPosition());
                    String telefone = "+55" + empresas.getTelefone();
                    String message = "Olá, vim pelo BEAUTY SALON";
                    Toast.makeText(context, "Abrindo o whatsapp", Toast.LENGTH_SHORT).show();
                        context.startActivity( new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", telefone, message)
                                )
                            )
                        );
                }
            });

            btnLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String clienteLogado = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                    empresas = empresasArrayList.get(getAdapterPosition());

                    Favoritos favoritos = new Favoritos();
                    favoritos.setuIdEmpresa(empresas.getId());
                    favoritos.setuIdCliente(clienteLogado);

                    FirebaseFirestore.getInstance().collection("favoritos").add(favoritos)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(context, "Adicionado ao meus favoritos", Toast.LENGTH_LONG).show();
                            btnLove.setEnabled(false);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Teste", e.getMessage());
                        }
                    });
                }
            });
        }
    }
}
