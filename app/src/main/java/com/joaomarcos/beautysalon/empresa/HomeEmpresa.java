package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Categoria;

public class HomeEmpresa extends AppCompatActivity {

    private ImageView img_comany, nav_logo;
    private TextView nome_loja, tipo_categoria, descricao, text_nome_empresa;
    private FloatingActionButton btnWhatsapp, btnLove, btnAvaliar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empresa);
        inicarComponente();
        footerNavigation();
    }

    private void inicarComponente() {
        img_comany = findViewById(R.id.nav_comany);
        nome_loja = findViewById(R.id.nome_loja);
        text_nome_empresa = findViewById(R.id.text_nome_empresa);
        tipo_categoria = findViewById(R.id.tipo_categoria);
        descricao = findViewById(R.id.descricao);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnLove = findViewById(R.id.btnLove);
        btnAvaliar = findViewById(R.id.btnAvaliar);
        nav_logo = findViewById(R.id.nav_logo);
    }

    private void footerNavigation() {
        img_comany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmpresa.this, ProfileEmpresa.class);
                startActivity(intent);
            }
        });
        nav_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListarCategoria.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String nome, descricaos;
        StringBuilder categorias = new StringBuilder();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("empresa")
                .document(uid)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            nome_loja.setText(value.getString("nomeEmpresa"));
                            tipo_categoria.setText(value.getString("categoriaPricipal"));
                            descricao.setText(value.getString("descricao"));
                            text_nome_empresa.setText(value.getString("nomeEmpresa"));
                            categorias.append(tipo_categoria.getText().toString());
                        }
                    }
                });

        FirebaseFirestore.getInstance().collection("categoria")
                .whereEqualTo("uidEmpresa", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    Categoria cat = doc.getDocument().toObject(Categoria.class);
                                    categorias
                                            .append(", ")
                                            .append(cat.getNome())
                                            .append(", ");
                                }
                            }
                            tipo_categoria.setText(categorias);
                        }
                    }
                });

    }
}