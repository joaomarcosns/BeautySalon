package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.adapter.ListCategoria;
import com.joaomarcos.beautysalon.objeto.Categoria;
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.ArrayList;
import java.util.Objects;

public class ListarCategoria extends AppCompatActivity {

    private ImageView img_home;
    private ImageView img_comany;

    private FloatingActionButton add;
    private RecyclerView recyclerView;
    private LinearLayout vazio;
    ArrayList<Categoria> categoriaArrayList;
    ListCategoria listCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_categoria);
        inicarComponente();
        footerNavigation();
        String uuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseFirestore.getInstance().collection("categoria")
                .whereEqualTo("uidEmpresa", uuid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            System.out.println("Teste error");
                            return;
                        }
                        assert value != null;
                        if (value.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            vazio.setVisibility(View.VISIBLE);
                        }
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                Categoria cat = doc.getDocument().toObject(Categoria.class);
                                cat.setId(doc.getDocument().getId());
                                categoriaArrayList.add(cat);
                            }
                            listCategoria.notifyDataSetChanged();
                        }
                    }
                });

        telaCadastro();
    }

    private void telaCadastro() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
                startActivity(new Intent(getApplicationContext(), CadastarCategoria.class));
            }
        });
    }

    private void footerNavigation() {
        img_comany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileEmpresa.class));
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomeEmpresa.class));
            }
        });
    }

    private void inicarComponente() {
        img_home = findViewById(R.id.img_home);
        img_comany = findViewById(R.id.nav_comany);

        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoriaArrayList = new ArrayList<>();
        listCategoria = new ListCategoria(ListarCategoria.this, categoriaArrayList);
        recyclerView.setAdapter(listCategoria);

        vazio = findViewById(R.id.vazio);
        add = findViewById(R.id.add);
    }

    private void editar() {
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        String uuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        DocumentReference documentReference = dataBase.collection("empresa").document(uuid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentReference != null) {
                    Empresas empresas = new Empresas();
                    assert documentSnapshot != null;
                    empresas.setId(documentSnapshot.getString("id"));
                    empresas.setNomeEmpresa(documentSnapshot.getString("nomeEmpresa"));
                    empresas.setNomeDono(documentSnapshot.getString("nomeDono"));
                    empresas.setCpfDono(documentSnapshot.getString("cpfDono"));
                    empresas.setCategoriaPricipal(documentSnapshot.getString("categoriaPricipal"));
                    empresas.setDescricao(documentSnapshot.getString("descricao"));
                    empresas.setTelefone(documentSnapshot.getString("telefone"));
                    Intent intent = new Intent(getApplicationContext(), CadastarCategoria.class);
                    intent.putExtra("empresa", empresas);
                    startActivity(intent);
                    System.out.println(empresas.toString());
                }
            }
        });
    }
}