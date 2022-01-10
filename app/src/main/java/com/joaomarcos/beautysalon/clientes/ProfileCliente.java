package com.joaomarcos.beautysalon.clientes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.joaomarcos.beautysalon.AlterarSenha;
import com.joaomarcos.beautysalon.MainActivity;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.objeto.Clientes;

public class ProfileCliente extends AppCompatActivity {

    private ImageView img_home;
    private ImageView img_search;
    private ImageView img_love;

    private TextView text_nome;
    private TextView text_cpf;
    private TextView text_telefone;
    private TextView text_email;

    private ExtendedFloatingActionButton btn_sair;
    private ExtendedFloatingActionButton btn_editar;
    private ExtendedFloatingActionButton btn_apagar;
    private ExtendedFloatingActionButton btn_atualizar_senha;

    FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cliente);
        inicarComponente();
        footerNavigation();
        deslogar();
        editar();
        deletar();
        atualizarSenha();
    }

    private void atualizarSenha() {
        btn_atualizar_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AlterarSenha.class));

            }
        });
    }

    private void deletar() {
        btn_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileCliente.this);
                builder.setTitle("Atenção");
                builder.setMessage("Tem certeza que deseja excluir a sua conta?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apagar();
                    }
                });
                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void editar() {
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DocumentReference documentReference = dataBase.collection("cliente").document(uuid);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if (documentReference != null) {
                            Clientes cliente = new Clientes();
                            cliente.setId(documentSnapshot.getString("id"));
                            cliente.setNome(documentSnapshot.getString("nome"));
                            cliente.setCpf(documentSnapshot.getString("cpf"));
                            cliente.setTelefone(documentSnapshot.getString("telefone"));
                            cliente.setEmail(documentSnapshot.getString("email"));
                            Intent intent = new Intent(getApplicationContext(), AtualizarPerfilClienteActivity.class);
                            intent.putExtra("cliente", cliente);
                            startActivity(intent);
                            System.out.println(cliente.toString());
                        }
                    }
                });
            }

        });
    }

    private void deslogar() {
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void inicarComponente() {
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_love = findViewById(R.id.img_love);

        text_nome = findViewById(R.id.text_nome);
        text_cpf = findViewById(R.id.text_cpf);
        text_telefone = findViewById(R.id.text_telefone);
        text_email = findViewById(R.id.text_email);

        btn_sair = findViewById(R.id.btn_sair);
        btn_editar = findViewById(R.id.btm_editar);
        btn_apagar = findViewById(R.id.btn_apagar);
        btn_atualizar_senha = findViewById(R.id.btn_atualizar_senha);
    }

    private void footerNavigation() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileCliente.this, HomeCliente.class);
                startActivity(intent);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileCliente.this, Search.class);
                startActivity(intent);
            }
        });

        img_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileCliente.this, LoveEmpresa.class);
                startActivity(intent);
            }
        });
    }

    private void apagar() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        assert currentUser != null;
        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    FirebaseFirestore.getInstance().collection("cliente")
                            .document(currentUser.getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.signOut();
                                System.out.println("ok");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else System.out.println("Error Colections");
                        }
                    });
                }else System.out.println("Erro usuario");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = dataBase.collection("cliente").document(uuid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentReference != null) {
                    text_nome.setText(documentSnapshot.getString("nome"));
                    text_cpf.setText(documentSnapshot.getString("cpf"));
                    text_telefone.setText(documentSnapshot.getString("telefone"));
                    text_email.setText(email);
                }
            }
        });
    }
}