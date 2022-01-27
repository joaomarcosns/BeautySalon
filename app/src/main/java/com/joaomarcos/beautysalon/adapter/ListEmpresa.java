package com.joaomarcos.beautysalon.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Avaliacao;
import com.joaomarcos.beautysalon.objeto.Categoria;
import com.joaomarcos.beautysalon.objeto.Empresas;
import com.joaomarcos.beautysalon.objeto.Favoritos;

import java.util.ArrayList;
import java.util.Objects;

public class ListEmpresa extends RecyclerView.Adapter<ListEmpresa.MyViewHolder> {

    Context context;
    ArrayList<Empresas> empresasArrayList;
    Empresas empresas;
    ArrayList<Categoria> categoriaArrayList;

    public ListEmpresa(Context context, ArrayList<Empresas> empresasArrayList, ArrayList<Categoria> categoriaArrayList) {
        this.context = context;
        this.empresasArrayList = empresasArrayList;
        this.categoriaArrayList = categoriaArrayList;
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
        StringBuilder categoriaString = new StringBuilder();

        for (int i = 0; i < categoriaArrayList.size(); i++) {
            if (empresasArrayList.get(position).getId().equals(categoriaArrayList.get(i).getUidEmpresa())) {
                categoriaString
                        .append(empresasArrayList.get(position)
                                .getCategoriaPricipal())
                        .append(", ")
                        .append(categoriaArrayList.get(i).getNome())
                        .append(", ");
            }
        }

        holder.nome_loja.setText(emp.getNomeEmpresa());
        holder.tipo_categoria.setText(categoriaString);
        holder.descricao.setText(emp.getDescricao());
    }

    @Override
    public int getItemCount() {
        return empresasArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nome_loja;
        private final TextView tipo_categoria;
        private final TextView descricao;
        private final FloatingActionButton btnWhatsapp;
        private final FloatingActionButton btnLove;
        private final FloatingActionButton btnAvaliar;
        private final RatingBar rating_bar_indicator;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome_loja = itemView.findViewById(R.id.nome_loja);
            tipo_categoria = itemView.findViewById(R.id.tipo_categoria);
            descricao = itemView.findViewById(R.id.descricao);
            btnWhatsapp = itemView.findViewById(R.id.btnWhatsapp);
            btnLove = itemView.findViewById(R.id.btnLove);
            btnAvaliar = itemView.findViewById(R.id.btnAvaliar);
            rating_bar_indicator = itemView.findViewById(R.id.rating_bar_indicator);

            btnWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    empresas = empresasArrayList.get(getAdapterPosition());
                    String telefone = "+55" + empresas.getTelefone();
                    String message = "Olá, vim pelo BEAUTY SALON";
                    Toast.makeText(context, "Abrindo o whatsapp", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
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

            // Avaliar empresa
            btnAvaliar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                    bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);
                    bottomSheetDialog.setCanceledOnTouchOutside(false);
                    RatingBar rating_bar_avaliar = bottomSheetDialog.findViewById(R.id.rating_bar_avaliar);
                    Button btnAvaliarSubmit = bottomSheetDialog.findViewById(R.id.btnAvaliarSubmit);

                    assert btnAvaliarSubmit != null;
                    btnAvaliarSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            assert rating_bar_avaliar != null;
                            empresas = empresasArrayList.get(getAdapterPosition());
                            Avaliacao avaliacao = new Avaliacao();
                            avaliacao.setEmpresas(empresas);
                            avaliacao.setAvalicao(rating_bar_avaliar.getRating());

                            FirebaseFirestore.getInstance().collection("avaliacao")
                                    .add(avaliacao)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "AAAAAa", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
                    bottomSheetDialog.show();
                }
            });
        }
    }
}
