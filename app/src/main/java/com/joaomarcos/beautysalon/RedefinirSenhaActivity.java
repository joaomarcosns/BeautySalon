package com.joaomarcos.beautysalon;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class RedefinirSenhaActivity extends AppCompatActivity {

    private Button btnRedefinirSenha;
    private EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);
        inicarComponentes();

        btnRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });
    }

    private void validarCampos() {
        String email = editEmail.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o campo", Toast.LENGTH_LONG).show();
        } else redefinirSenha(email);
    }

    private void redefinirSenha(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Um e-mail foi enviado para você," +
                            "para fazer a sua redefinição de senha", Toast.LENGTH_LONG).show();
                    onBackPressed();
                    finish();
                } else {
                    String error = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        error = "E-mail inválido";
                    } catch (Exception e) {
                        Log.d("Teste", e.getMessage());
                        error = "E-mail não cadastrado";
                    }
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void inicarComponentes() {
        editEmail = findViewById(R.id.editEmail);
        btnRedefinirSenha = findViewById(R.id.btnRedefinirSenha);
    }
}