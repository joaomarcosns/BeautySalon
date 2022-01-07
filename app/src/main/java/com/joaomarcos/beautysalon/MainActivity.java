package com.joaomarcos.beautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.clientes.FormLoginCliente;
import com.joaomarcos.beautysalon.clientes.HomeCliente;
import com.joaomarcos.beautysalon.empresa.FormLoginEmpresa;
import com.joaomarcos.beautysalon.empresa.HomeEmpresa;

public class MainActivity extends AppCompatActivity {

    private Button btn_empresa;
    private Button btn_cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IniciarComponente();
        navigation();
//        verificarUsuarioLogado();
    }

    private void IniciarComponente() {
        btn_empresa = findViewById(R.id.btn_empresa);
        btn_cliente = findViewById(R.id.btn_cliente);
    }

    private void navigation() {
        btn_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormLoginEmpresa.class);
                startActivity(intent);
            }
        });
        btn_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormLoginCliente.class);
                startActivity(intent);
            }
        });
    }

//    public void verificarUsuarioLogado() {
//        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
//        if (usuarioAtual != null) {
//            verificarNivelConta(usuarioAtual.getUid());
//        }else {
////            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////            startActivity(intent);
////            finish();
//        }
//    }

//    private void verificarNivelConta(String uid) {
//
//        FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
//        DocumentReference ClientedocumentReference = dataBase.collection("cliente").document(uid);
//        DocumentReference EmpresadocumentReference = dataBase.collection("empresa").document(uid);
//
//        ClientedocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.get("nivelAcesso") != null) {
//                    Toast.makeText(getApplicationContext(), "Seja Bem vindo", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), HomeCliente.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
//        });
//
//        EmpresadocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.get("nivelAcesso") != null) {
//                    Toast.makeText(getApplicationContext(), "Seja Bem vindo", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), HomeEmpresa.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            }
//        });
//    }
}