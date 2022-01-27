package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Categoria;
import com.joaomarcos.beautysalon.objeto.Empresas;

public class CadastarCategoria extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner dropdown;
    private String categoria;
    private Button btn_cadastrar;
    private ProgressBar progressBar_login;
    private Empresas empresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastar_categoria);
        iniciarComponente();
        Intent intent = getIntent();
        if (intent.hasExtra("empresa")) {
            empresas = (Empresas) intent.getSerializableExtra("empresa");
            String categoriaEscolhida = empresas.getCategoriaPricipal();
            dropdown(categoriaEscolhida);
        }
        cadastar();
    }

    private void cadastar() {
        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar_login.setVisibility(View.VISIBLE);
                String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Categoria cat = new Categoria();
                if (categoria == null) {
                    categoria = "Limpeza de Pele";
                }
                cat.setNome(categoria);
                cat.setUidEmpresa(uuid);

                System.out.println(cat.toString());

                FirebaseFirestore.getInstance().collection("categoria")
                        .add(cat)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Categoria Cadastrada com Sucesso", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), ListarCategoria.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private void iniciarComponente() {
        dropdown = findViewById(R.id.spinner1);
        btn_cadastrar = findViewById(R.id.btn_cadastrar);
        progressBar_login = findViewById(R.id.progressBar_login);
    }

    private void dropdown(String categoriaEscolhida) {
        String[] items = new String[]{};
        switch (categoriaEscolhida) {
            case "Manicure Pedicure":
                items = new String[]{
                        "Depilação", "Maquiagem", "Limpeza de Pele",
                        "Massagem", "Cabeleireiro"
                };
                break;
            case "Depilação":
                items = new String[]{
                        "Manicure Pedicure",
                        "Maquiagem", "Limpeza de Pele",
                        "Massagem", "Cabeleireiro"
                };
                break;
            case "Maquiagem":
                items = new String[]{
                        "Limpeza de Pele",
                        "Massagem", "Cabeleireiro",
                        "Depilação",
                        "Manicure Pedicure",
                };
                break;
            case "Limpeza de Pele":
                items = new String[]{
                        "Massagem",
                        "Cabeleireiro",
                        "Depilação",
                        "Manicure Pedicure",
                        "Maquiagem"
                };
                break;
            case "Massagem":
                items = new String[]{
                        "Cabeleireiro",
                        "Depilação",
                        "Manicure Pedicure",
                        "Maquiagem",
                        "Limpeza de Pele"
                };
                break;
            case "Cabeleireiro":
                items = new String[]{
                        "Manicure Pedicure",
                        "Depilação",
                        "Maquiagem",
                        "Limpeza de Pele",
                        "Massagem",
                };
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_drop_down, items);
        dropdown.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.item_drop_down);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String item = parent.getItemAtPosition(position).toString();
                    categoria = item;
                    Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}