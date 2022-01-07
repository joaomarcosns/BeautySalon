package com.joaomarcos.beautysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.clientes.HomeCliente;
import com.joaomarcos.beautysalon.empresa.HomeEmpresa;

import java.util.Objects;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
                if (usuarioAtual != null) {
                    verificaNivelConta(usuarioAtual.getUid());
                } else {
                    startActivity(new Intent(Loading.this, MainActivity.class));
                    finish();
                }
            }
        }, 1200);
    }

    private void verificaNivelConta(String uid) {
        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
        DocumentReference ClientedocumentReference = dataBase.collection("cliente").document(uid);
        DocumentReference EmpresadocumentReference = dataBase.collection("empresa").document(uid);

        ClientedocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
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

        EmpresadocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.get("nivelAcesso") != null) {
                    Toast.makeText(getApplicationContext(), "Seja Bem vindo", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeEmpresa.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }
}