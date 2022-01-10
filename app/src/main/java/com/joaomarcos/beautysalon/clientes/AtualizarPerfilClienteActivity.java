package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Clientes;

import java.util.Objects;

public class AtualizarPerfilClienteActivity extends AppCompatActivity {
    Clientes clientes;

    private EditText edit_nome;
    private EditText edit_cpf;
    private EditText edit_telefone;
    private Button btn_atualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_perfil_cliente);
        iniciarComponente();
        maskEditInput();
        Intent intent = getIntent();
        if (intent.hasExtra("cliente")) {
            clientes = (Clientes) intent.getSerializableExtra("cliente");
            edit_nome.setText(clientes.getNome());
            edit_cpf.setText(clientes.getCpf());
            edit_telefone.setText(clientes.getTelefone());
        }

        btn_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificar(v);
            }
        });



    }

    private void verificar(View v) {
        String nome = edit_nome.getText().toString();
        String cpf = edit_cpf.getText().toString();
        String telefone = edit_telefone.getText().toString();

        if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty()) {
            Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
        }else {
            atualizar(v);
        }
    }

    private void atualizar(View v) {
        String nome = edit_nome.getText().toString();
        String cpf = edit_cpf.getText().toString();
        String telefone = edit_telefone.getText().toString();

        Clientes cl = new Clientes();
        cl.setId(clientes.getId());
        cl.setEmail(clientes.getEmail());
        cl.setNome(nome);
        cl.setCpf(cpf);
        cl.setNivelAcesso(1);
        cl.setTelefone(telefone);
        FirebaseFirestore.getInstance().collection("cliente")
                .document(this.clientes.getId())
                .set(cl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Conta Atualizada", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), ProfileCliente.class));
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

    private void iniciarComponente() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_cpf = findViewById(R.id.edit_cpf);
        edit_telefone = findViewById(R.id.edit_telefone);
        btn_atualizar = findViewById(R.id.btn_atualizar);
    }

    private void maskEditInput() {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(edit_telefone, smfTelefone);
        edit_telefone.addTextChangedListener(mtwTelefone);

        SimpleMaskFormatter smfCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCpf = new MaskTextWatcher(edit_cpf, smfCpf);
        edit_cpf.addTextChangedListener(mtwCpf);
    }
}