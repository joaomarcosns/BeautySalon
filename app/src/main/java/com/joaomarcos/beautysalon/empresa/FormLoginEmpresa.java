package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.clientes.FormLoginCliente;
import com.joaomarcos.beautysalon.clientes.HomeCliente;

import java.util.Objects;

public class FormLoginEmpresa extends AppCompatActivity {

    private TextView text_tela_cadastro_empresa;
    private EditText edit_email;
    private EditText edit_senha;

    private Button btnLogin;
    private ProgressBar progressBar_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login_empresa);
        InicarComponentes();
        telaCadastro();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(v);
                progressBar_login.setVisibility(View.VISIBLE);
            }
        });
    }

    private void validar(View v) {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();
        if (email.isEmpty()|| senha.isEmpty()) {
            Snackbar snackbar = Snackbar.make(v, "Preencha os campos", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(Color.WHITE);
            snackbar.setTextColor(Color.BLACK);
            snackbar.show();
        }else {
            login(v);
        }
}

    private void login(View v) {
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();

        Task<AuthResult> authResultTask = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String uuid = Objects.requireNonNull(Objects.requireNonNull(authResultTask.getResult()).getUser()).getUid();
                    verificarConta(uuid);
                    Log.d("Teste", uuid);
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
        DocumentReference EmpresaDocumentReference = dataBase.collection("empresa").document(uuid);

        EmpresaDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("nivelAcesso") != null) {
                    Toast.makeText(getApplicationContext(), "Seja Bem vindo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeEmpresa.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    private void telaCadastro() {
        text_tela_cadastro_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Usuário informado pertence a esse Login", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FormLoginEmpresa.this,
                        FormCadastroEmpresa.class);
                startActivity(intent);
            }
        });
    }


    private void InicarComponentes() {
        text_tela_cadastro_empresa =findViewById(R.id.tela_cadasto_empresa);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar_login = findViewById(R.id.progressBar_login);
    }
}