package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.joaomarcos.beautysalon.R;

public class FormLoginEmpresa extends AppCompatActivity {

    private TextView text_tela_cadastro_empresa;
    private EditText edit_email;
    private EditText edit_senha;

    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login_empresa);
        InicarComponentes();
        telaCadastro();
        validar();
    }

    private void validar() {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        btnLogin.setOnClickListener(new View.OnClickListener() {
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

        Intent intent = new Intent(FormLoginEmpresa.this, HomeEmpresa.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void telaCadastro() {
        text_tela_cadastro_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }
}