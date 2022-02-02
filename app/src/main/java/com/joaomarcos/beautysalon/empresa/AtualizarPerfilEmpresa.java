package com.joaomarcos.beautysalon.empresa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.clientes.ProfileCliente;
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.Objects;

public class AtualizarPerfilEmpresa extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Empresas empresas;
    private EditText edit_nome_empresa;
    private EditText edit_nome_proetario;
    private EditText edit_cpf_proetario;
    private EditText edit_telefone;
    private EditText edit_descricao;
    private Spinner dropdown;

    private Button btn_login;
    private ProgressBar progressBar_login;
    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_perfil_empresa);
        iniciarComponente();
        maskEditInput();
        Intent intent = getIntent();
        if (intent.hasExtra("empresa")) {
            empresas = (Empresas) intent.getSerializableExtra("empresa");
            edit_nome_empresa.setText(empresas.getNomeEmpresa());
            edit_nome_proetario.setText(empresas.getNomeDono());
            edit_cpf_proetario.setText(empresas.getCpfDono());
            edit_descricao.setText(empresas.getDescricao());
            edit_telefone.setText(empresas.getTelefone());
            String categoriaEscolhida = empresas.getCategoriaPricipal();
            dropdown(categoriaEscolhida);
        }
        scrollViewDescricao();
        validarCampos();
    }


    private void validarCampos() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeEmpresa = edit_nome_empresa.getText().toString();
                String nomeProprietario = edit_nome_proetario.getText().toString();
                String cpfProprietario = edit_cpf_proetario.getText().toString();
                String telefoneEmpresa = edit_telefone.getText().toString();
                String descricao = edit_descricao.getText().toString();
                if (nomeEmpresa.isEmpty() || nomeProprietario.isEmpty() || cpfProprietario.isEmpty()
                        || telefoneEmpresa.isEmpty() || descricao.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    return;
                } else cadastra(nomeEmpresa, nomeProprietario, cpfProprietario, telefoneEmpresa, descricao, v);
            }
        });

    }

    private void cadastra(String nomeEmpresa, String nomeProprietario, String cpfProprietario, String telefoneEmpresa, String descricao, View v) {
        Empresas emp = new Empresas();
        emp.setId(empresas.getId());
        emp.setNomeEmpresa(nomeEmpresa);
        emp.setNomeDono(nomeProprietario);
        emp.setCpfDono(cpfProprietario);
        emp.setDescricao(descricao);
        emp.setTelefone(telefoneEmpresa);
        emp.setNivelAcesso(2);
        if (categoria == null) {
            emp.setCategoriaPricipal(empresas.getCategoriaPricipal());
        }else {
            emp.setCategoriaPricipal(categoria);
        }

        FirebaseFirestore.getInstance().collection("empresa")
                .document(emp.getId())
                .set(emp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Conta Atualizada", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), ProfileEmpresa.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar snackbar = Snackbar.make(v, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
            }
        });
    }

    private void dropdown(String categoriaEscolhida) {
        String[] items = new String[]{};
        switch (categoriaEscolhida) {
            case "Manicure Pedicure":
                items = new String[]{
                        "Manicure Pedicure",
                        "Depilação", "Maquiagem", "Limpeza de Pele",
                        "Massagem", "Cabeleireiro"
                };
                break;
            case "Depilação":
                items = new String[]{
                        "Depilação",
                        "Manicure Pedicure",
                        "Maquiagem", "Limpeza de Pele",
                        "Massagem", "Cabeleireiro"
                };
                break;
            case "Maquiagem":
                items = new String[]{
                        "Maquiagem", "Limpeza de Pele",
                        "Massagem", "Cabeleireiro",
                        "Depilação",
                        "Manicure Pedicure",
                };
                break;
            case "Limpeza de Pele":
                items = new String[]{
                        "Limpeza de Pele",
                        "Massagem",
                        "Cabeleireiro",
                        "Depilação",
                        "Manicure Pedicure",
                        "Maquiagem"
                };
                break;
            case "Massagem":
                items = new String[]{
                        "Massagem",
                        "Cabeleireiro",
                        "Depilação",
                        "Manicure Pedicure",
                        "Maquiagem",
                        "Limpeza de Pele"
                };
                break;
            case "Cabeleireiro":
                items = new String[]{
                        "Cabeleireiro",
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

    @SuppressLint("ClickableViewAccessibility")
    private void scrollViewDescricao() {
        edit_descricao.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }

    private void maskEditInput() {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(edit_telefone, smfTelefone);
        edit_telefone.addTextChangedListener(mtwTelefone);

        SimpleMaskFormatter smfCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCpf = new MaskTextWatcher(edit_cpf_proetario, smfCpf);
        edit_cpf_proetario.addTextChangedListener(mtwCpf);
    }

    private void iniciarComponente() {
        edit_nome_empresa = findViewById(R.id.edit_nome_empresa);
        edit_nome_proetario = findViewById(R.id.edit_nome_proetario);
        edit_cpf_proetario = findViewById(R.id.edit_cpf_proetario);
        btn_login = findViewById(R.id.btn_login);
        progressBar_login = findViewById(R.id.progressBar_login);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_descricao = findViewById(R.id.edit_descricao);
        dropdown = findViewById(R.id.spinner1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}