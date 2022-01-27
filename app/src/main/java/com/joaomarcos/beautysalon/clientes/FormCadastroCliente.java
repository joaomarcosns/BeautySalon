package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Clientes;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;


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
                }else if(validarCpf(cpf)) {
                    Snackbar snackbar = Snackbar.make(v, "Essa conta ja existe", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
                else {
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
        cliente.setNivelAcesso(1);

        Task<AuthResult> authResultTask = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = Objects.requireNonNull(Objects.requireNonNull(authResultTask.getResult()).getUser());
                    cliente.setId(user.getUid());
                    FirebaseFirestore.getInstance().collection("cliente").document(user.getUid()).set(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar snackbar = Snackbar.make(v, "Cadastro relaizado com sucesso", Snackbar.LENGTH_LONG);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();

                            Intent intent = new Intent(getApplicationContext(), HomeCliente.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Teste", e.getMessage());
                        }
                    });
                }else {
                    String error = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        error = "A senha deve conter no mínino 6 caracteres";
                    } catch (FirebaseAuthUserCollisionException e) {
                        error = "A Conta já criada";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        error = "E-mail inválido";
                    } catch (Exception e) {
                        error = "Erro ao Cadastar Cliente";
                    }
                    Snackbar snackbar = Snackbar.make(v, error, Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TESTE", e.getMessage());
            }
        });
    }

    public boolean validarCpf(String cpf) {
        final boolean[] valido = {false};
        FirebaseFirestore.getInstance().collection("cliente")
                .whereEqualTo("cpf", cpf)
                .addSnapshotListener((value, error) -> {
                    if (value != null && value.getDocuments().size() > 0) {
                        valido[0] = false;
                    }
                });
        return valido[0];
    }

}