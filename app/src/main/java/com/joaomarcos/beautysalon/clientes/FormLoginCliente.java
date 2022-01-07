package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.RedefinirSenhaActivity;
import com.joaomarcos.beautysalon.empresa.FormLoginEmpresa;

import java.util.Objects;

public class FormLoginCliente extends AppCompatActivity {

    private TextView text_tela_cadastro_cliente;
    private TextView esqueci_a_senha;
    private EditText edit_email;
    private EditText edit_senha;
    private Button btn_entrar;
    private ProgressBar progressBar_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login_cliente);
        InicarComponentes();
        telaRedefinirSenha();

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos(v);
            }
        });

        text_tela_cadastro_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLoginCliente.this, FormCadastroCliente.class);
                startActivity(intent);
            }
        });

    }

    private void telaRedefinirSenha() {
        esqueci_a_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RedefinirSenhaActivity.class));
            }
        });
    }


    private void InicarComponentes() {
        text_tela_cadastro_cliente = findViewById(R.id.tela_cadasto_cliente);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        btn_entrar = findViewById(R.id.btn_entrar);
        progressBar_login = findViewById(R.id.progressBar_login);
        esqueci_a_senha = findViewById(R.id.esqueci_a_senha);
    }

    private void validarCampos(View v) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();
        if (email.isEmpty() || senha.isEmpty()) {
            Snackbar snackbar = Snackbar.make(v, "Preencha os Campos", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        } else {
            progressBar_login.setVisibility(View.VISIBLE);
            login(v);
        }
    }

    private void login(View v) {
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();

        Task<AuthResult> authResultTask =   FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uuid = Objects.requireNonNull(Objects.requireNonNull(authResultTask.getResult()).getUser()).getUid();
                    verificarConta(uuid);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Teste", e.getMessage());
                Snackbar snackbar = Snackbar.make(v, "E-mail ou senha Invalidos", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        });
    }

    private void verificarConta(String uuid) {
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        DocumentReference ClienteDocumentReference = dataBase.collection("cliente").document(uuid);
        DocumentReference EmpresaDocumentReference = dataBase.collection("empresa").document(uuid);

        ClienteDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("nivelAcesso") != null) {
                    Toast.makeText(getApplicationContext(), "Seja Bem vindo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeCliente.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        EmpresaDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("nivelAcesso") != null) {
                    Toast.makeText(getApplicationContext(), "Usuário informado pertence a esse Login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), FormLoginEmpresa.class);
                    startActivity(intent);
                }
            }
        });

    }

}