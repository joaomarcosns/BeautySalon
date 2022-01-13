package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.joaomarcos.beautysalon.R;

public class HomeEmpresa extends AppCompatActivity {

    private ImageView img_comany;
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
    }

    private void footerNavigation(){
        img_comany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmpresa.this, ProfileEmpresa.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

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
                        }
                    }
                });
    }
}