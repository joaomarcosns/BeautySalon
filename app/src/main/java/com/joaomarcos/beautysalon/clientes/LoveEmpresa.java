package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.adapter.LisFavoritos;
import com.joaomarcos.beautysalon.objeto.Empresas;
import com.joaomarcos.beautysalon.objeto.Favoritos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class LoveEmpresa extends AppCompatActivity {

    private ImageView img_home;
    private ImageView img_profile;
    private ImageView img_search;

    private RecyclerView recyclerView;
    ArrayList<Favoritos> favoritosArrayList;
    ArrayList<Empresas> empresasArrayList;
    LisFavoritos myAdapter;
    FirebaseFirestore db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_empresa);
        iniciarComponente();
        footNavigation();
        EventChangeListener();
    }


    private void iniciarComponente() {
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_profile = findViewById(R.id.img_user);
        recyclerView = findViewById(R.id.idRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        favoritosArrayList = new ArrayList<Favoritos>();
        empresasArrayList = new ArrayList<Empresas>();
        myAdapter = new LisFavoritos(LoveEmpresa.this, favoritosArrayList);

        recyclerView.setAdapter(myAdapter);
//        EventChangeListener();

    }

    private  void EventChangeListener() {
        String clienteLogado = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("favoritos").whereEqualTo("uIdCliente", clienteLogado).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("Teste", error.getMessage());
                }

                for (DocumentChange doc: value.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {
                        Favoritos fav = new Favoritos();
                        fav.setId(doc.getDocument().getId());
                        fav.setuIdCliente(doc.getDocument().getString("uIdCliente"));
                        fav.setuIdEmpresa(doc.getDocument().getString("uIdEmpresa"));
                        favoritosArrayList.add(fav);
                    }


                    db.collection("empresa")
                            .whereEqualTo("id",doc.getDocument().getString("uIdEmpresa"))
                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                @Override
                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                    if (error != null) {
                                        Log.d("Teste", error.getMessage());
                                    }

                                    for (DocumentChange documentChange : value.getDocumentChanges()) {
                                        if (documentChange.getType() == DocumentChange.Type.ADDED) {
                                            Empresas empresas = new Empresas();
                                            empresas.setId(documentChange.getDocument().getId());
                                            empresasArrayList.add(empresas);
                                        }
                                    }
                                }
                            });
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void footNavigation() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoveEmpresa.this, HomeCliente.class);
                startActivity(intent);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoveEmpresa.this, Search.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                startActivity(intent);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoveEmpresa.this, ProfileCliente.class);
                startActivity(intent);
            }
        });

    }

//    private void entrarEmContato() {
//        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String telefone = "+5577991620945";
//                String message = "Olá, vim pelo BEAUTY SALON";
//                Toast.makeText(LoveEmpresa.this, "Indo ao whatsapp", Toast.LENGTH_SHORT).show();
//                startActivity(
//                        new Intent(Intent.ACTION_VIEW,
//                                Uri.parse(
//                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
//                                                telefone,
//                                                message
//                                        )
//                                )
//                        )
//                );
//            }
//        });
//    }

//    private void onLove() {
//        btnLove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Removidos dos seus favoritos", Toast.LENGTH_LONG);
//                toast.show();
//            }
//        });
//    }
}