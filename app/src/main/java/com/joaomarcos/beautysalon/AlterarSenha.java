package com.joaomarcos.beautysalon;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class AlterarSenha extends AppCompatActivity {
    private EditText editAntigaSenha;
    private EditText editNovaSenha;
    private EditText editNovaSenhaConfirma;
    private Button btnRedefinirSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_senha);
        iniciarComponentes();
        validarCampos();
    }

    private void validarCampos() {
        btnRedefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldSenha = editAntigaSenha.getText().toString();
                String novaSenha = editNovaSenha.getText().toString();
                String novaSenhaComfirmar = editNovaSenhaConfirma.getText().toString();

                if (oldSenha.isEmpty() || novaSenha.isEmpty() || novaSenhaComfirmar.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, "Todos os campos são de preenchimento obrigatório", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    return;
                } else if (!novaSenha.equals(novaSenhaComfirmar)) {
                    Snackbar snackbar = Snackbar.make(v, "As senhas não coincidem", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                    return;
                } else atualizarSenha(oldSenha, novaSenha, v);
            }
        });
    }

    private void atualizarSenha(String oldSenha, String novaSenha, View v) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        AuthCredential credential = EmailAuthProvider
                .getCredential(Objects.requireNonNull(user.getEmail()), oldSenha);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(novaSenha).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Senha atualizar senhar com sucesso", Toast.LENGTH_LONG).show();
                                onBackPressed();
                                finish();
                            } else {
                                String error = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    error = "A senha deve conter no mínino 6 caracteres";
                                } catch (Exception e) {
                                    error = "Erro ao Cadastar Cliente";
                                }
                                Snackbar snackbar = Snackbar.make(v, error, Snackbar.LENGTH_LONG);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();
                            }
                        }
                    });
                } else {
                    System.out.println("Error auth failed");
                }
            }
        });
    }

    private void iniciarComponentes() {
        editAntigaSenha = findViewById(R.id.editAntigaSenha);
        editNovaSenha = findViewById(R.id.editNovaSenha);
        editNovaSenhaConfirma = findViewById(R.id.editNovaSenhaConfirma);
        btnRedefinirSenha = findViewById(R.id.btnRedefinirSenha);
    }
}