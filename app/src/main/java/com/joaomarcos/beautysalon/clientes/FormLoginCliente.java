package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.joaomarcos.beautysalon.R;

public class FormLoginCliente extends AppCompatActivity {

    private TextView text_tela_cadastro_cliente;
    private EditText edit_email;
    private EditText edit_senha;
    private Button btn_entrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login_cliente);
        InicarComponentes();
        validarCampos();

        text_tela_cadastro_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLoginCliente.this, FormCadastroCliente.class);
                startActivity(intent);
            }
        });

    }


    private void InicarComponentes() {
        text_tela_cadastro_cliente = findViewById(R.id.tela_cadasto_cliente);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        btn_entrar = findViewById(R.id.btn_entrar);
    }

    private void validarCampos() {
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email == null || senha == null) {
                    Snackbar snackbar = Snackbar.make(v, "Preencha os campos", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    login();
                }
            }
        });
    }

    private void login() {
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();

        Toast.makeText(this, "Seja Bem vindo", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, HomeCliente.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}