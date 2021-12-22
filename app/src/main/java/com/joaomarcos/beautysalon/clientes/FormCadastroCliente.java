package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.snackbar.Snackbar;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Clientes;


public class FormCadastroCliente extends AppCompatActivity {

    private EditText edit_nome;
    private EditText edit_cpf;
    private EditText edit_telefone;
    private EditText edit_email;
    private EditText edit_senha;
    private EditText edit_comfirma_senha;
    private Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_cliente);
        iniciarComponente();
        maskEditInput();
        validarCampos();
    }
    private void iniciarComponente() {
        edit_nome = findViewById(R.id.edit_nome);
        edit_cpf = findViewById(R.id.edit_cpf);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        edit_comfirma_senha  = findViewById(R.id.edit_comfirma_senha);
        btn_login = findViewById(R.id.btn_login);
    }

    private void maskEditInput() {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(edit_telefone, smfTelefone);
        edit_telefone.addTextChangedListener(mtwTelefone);

        SimpleMaskFormatter smfCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCpf = new MaskTextWatcher(edit_cpf, smfCpf);
        edit_cpf.addTextChangedListener(mtwCpf);
    }
    private void validarCampos() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = edit_nome.getText().toString();
                String cpf = edit_cpf.getText().toString();
                String telefone = edit_telefone.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();
                String comfirmarSenha = edit_comfirma_senha.getText().toString();

                if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty() || comfirmarSenha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else if (senha == comfirmarSenha) {
                    Snackbar snackbar = Snackbar.make(v, "As senhas não batem", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    cadastrarCliente(v);
                }
            }
        });
    }

    private void cadastrarCliente(View v) {
        String email = edit_email.getText().toString().trim();
        String senha = edit_senha.getText().toString().trim();
        String cpf = edit_cpf.getText().toString().trim();
        String nome = edit_nome.getText().toString().trim();
        String telefone = edit_telefone.getText().toString().trim();

        Clientes cliente = new Clientes();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
        cliente.setSenha(senha);

        Snackbar snackbar = Snackbar.make(v, "Cdastro relaizado com sucesso", Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.WHITE);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();

        Intent intent = new Intent(this, HomeCliente.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}