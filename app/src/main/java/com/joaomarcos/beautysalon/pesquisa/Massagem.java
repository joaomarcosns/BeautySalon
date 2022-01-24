package com.joaomarcos.beautysalon.pesquisa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.adapter.ListMassagem;
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.ArrayList;

public class Massagem extends AppCompatActivity {
    private RecyclerView recyclerView;
    ArrayList<Empresas> empresasArrayList;
    ListMassagem listMassagem;
    private LinearLayout vazio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_massagem);
        InicarComponente();

        FirebaseFirestore.getInstance().collection("empresa")
                .whereEqualTo("categoriaPricipal", "Massagem")
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
                                Empresas empresas = doc.getDocument().toObject(Empresas.class);
                                empresas.setId(doc.getDocument().getId());
                                empresasArrayList.add(empresas);
                                System.out.println(empresasArrayList);
                            }
                            listMassagem.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void InicarComponente() {
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        empresasArrayList = new ArrayList<Empresas>();
        listMassagem = new ListMassagem(Massagem.this, empresasArrayList);
        recyclerView.setAdapter(listMassagem);
        vazio = findViewById(R.id.vazio);
    }
}