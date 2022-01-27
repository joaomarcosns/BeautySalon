package com.joaomarcos.beautysalon.adapter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.empresa.ListarCategoria;
import com.joaomarcos.beautysalon.objeto.Categoria;

import java.util.ArrayList;

public class ListCategoria extends RecyclerView.Adapter<ListCategoria.MyViewHolder> {

    Context context;
    ArrayList<Categoria> categoriaArrayList;
    Categoria categoria;

    public ListCategoria(Context context, ArrayList<Categoria> categoriaArrayList) {
        this.context = context;
        this.categoriaArrayList = categoriaArrayList;
    }

    @NonNull
    @Override
    public ListCategoria.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_listagemdecategorias, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCategoria.MyViewHolder holder, int position) {
        Categoria categoria = categoriaArrayList.get(position);
        holder.nome_categoria.setText(categoria.getNome());
    }

    @Override
    public int getItemCount() {
        return categoriaArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nome_categoria;
        private FloatingActionButton btnDeletar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome_categoria = itemView.findViewById(R.id.nome_categoria);
            btnDeletar = itemView.findViewById(R.id.btnDeletar);


            btnDeletar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Atenção");
                    builder.setMessage("Tem certeza que deseja excluir?");
                    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            categoria = categoriaArrayList.get(getAdapterPosition());
                            FirebaseFirestore.getInstance().collection("categoria")
                                    .document(categoria.getId()).delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "Categoria Apagada Com Sucesso", Toast.LENGTH_LONG).show();
                                                context.startActivity(new Intent(context, ListarCategoria.class));
                                            }
                                        }
                                    });
                        }
                    });
                    builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                }
            });

        }
    }
}
