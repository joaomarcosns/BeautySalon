package com.joaomarcos.beautysalon.pesquisa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.adapter.LisManicurePedicure;
import com.joaomarcos.beautysalon.adapter.ListLimpezaPele;
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.ArrayList;

public class LimpezaPele extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout vazio;
    ArrayList<Empresas> empresasArrayList;
    ListLimpezaPele limpezaPele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limpeza_pele);
        InicarComponente();
        FirebaseFirestore.getInstance().collection("empresa")
                .whereEqualTo("categoriaPricipal", "Limpeza de Pele")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Teste", error.getMessage());
                            return;
                        }

                        assert value != null;
                        if (value.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            vazio.setVisibility(View.VISIBLE);
                        }
                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {
                                Empresas empresas  = doc.getDocument().toObject(Empresas.class);
                                empresas.setId(doc.getDocument().getId());
                                empresasArrayList.add(empresas);
                                System.out.println(empresasArrayList);
                            }
                            limpezaPele.notifyDataSetChanged();
                        }
                    }
                });
    }
    private void InicarComponente() {
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        empresasArrayList = new ArrayList<Empresas>();
        limpezaPele = new ListLimpezaPele(LimpezaPele.this, empresasArrayList);
        recyclerView.setAdapter(limpezaPele);
        vazio = findViewById(R.id.vazio);
    }
}