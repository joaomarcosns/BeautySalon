package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.joaomarcos.beautysalon.MainActivity;
import com.joaomarcos.beautysalon.R;

import org.w3c.dom.Text;

public class ProfileEmpresa extends AppCompatActivity {

    private ImageView img_home;
    private TextView text_nome_empresa;
    private TextView text_nome_proetario;
    private TextView text_cpf_proetario;
    private TextView text_categoria;
    private TextView text_descricao;
    private TextView text_telefone;
    private TextView text_email;

    private ExtendedFloatingActionButton btn_sair;
    private ExtendedFloatingActionButton btn_editar;

    FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private String uuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_empresa);
        inicarCompoenente();
        footerNavigation();
        deslogar();
    }

    private void deslogar() {
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void footerNavigation() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEmpresa.this, HomeEmpresa.class);
                startActivity(intent);
            }
        });
    }

    private void inicarCompoenente() {
        img_home = findViewById(R.id.img_home);
        btn_sair = findViewById(R.id.btn_sair);
        btn_editar = findViewById(R.id.btm_editar);

        text_nome_empresa = findViewById(R.id.text_nome_empresa);
        text_nome_proetario = findViewById(R.id.text_nome_proetario);
        text_cpf_proetario = findViewById(R.id.text_cpf_proetario);
        text_categoria = findViewById(R.id.text_categoria);
        text_descricao = findViewById(R.id.text_descricao);
        text_telefone = findViewById(R.id.text_telefone);
        text_email = findViewById(R.id.text_email);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = dataBase.collection("empresa").document(uuid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentReference != null) {
                    text_nome_empresa.setText(documentSnapshot.getString("nomeEmpresa"));
                    text_nome_proetario.setText(documentSnapshot.getString("nomeDono"));
                    text_cpf_proetario.setText(documentSnapshot.getString("cpfDono"));
                    text_categoria.setText(documentSnapshot.getString("categoriaPricipal"));
                    text_descricao.setText(documentSnapshot.getString("descricao"));
                    text_telefone.setText(documentSnapshot.getString("telefone"));
                    text_email.setText(email);
                }
            }
        });
    }
}