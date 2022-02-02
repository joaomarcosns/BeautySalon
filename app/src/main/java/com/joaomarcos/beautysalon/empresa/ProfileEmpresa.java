package com.joaomarcos.beautysalon.empresa;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.Objects;

public class ProfileEmpresa extends AppCompatActivity {

    private ImageView img_home, nav_logo;
    private TextView text_nome_empresa;
    private TextView text_nome_proetario;
    private TextView text_cpf_proetario;
    private TextView text_categoria;
    private TextView text_descricao;
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
        setContentView(R.layout.activity_profile_empresa);
        inicarCompoenente();
        footerNavigation();
        deslogar();
        deletar();
        scrollViewDescricao();
        editar();
        atualizarSenha();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void scrollViewDescricao() {
        text_descricao.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
    }

    private void editar() {
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uuid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                DocumentReference documentReference = dataBase.collection("empresa").document(uuid);
                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                        if (documentReference != null) {
                            Empresas empresas = new Empresas();
                            assert documentSnapshot != null;
                            empresas.setId(documentSnapshot.getString("id"));
                            empresas.setNomeEmpresa(documentSnapshot.getString("nomeEmpresa"));
                            empresas.setNomeDono(documentSnapshot.getString("nomeDono"));
                            empresas.setCpfDono(documentSnapshot.getString("cpfDono"));
                            empresas.setCategoriaPricipal(documentSnapshot.getString("categoriaPricipal"));
                            empresas.setDescricao(documentSnapshot.getString("descricao"));
                            empresas.setTelefone(documentSnapshot.getString("telefone"));
                            Intent intent = new Intent(getApplicationContext(), AtualizarPerfilEmpresa.class);
                            intent.putExtra("empresa", empresas);
                            startActivity(intent);
                            System.out.println(empresas.toString());

                        }
                    }
                });
            }
        });
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileEmpresa.this);
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

    private void apagar() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String id = firebaseAuth.getUid();
        System.out.println(id);
        assert id != null;
        FirebaseFirestore.getInstance().collection("empresa")
                .document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    assert currentUser != null;
                    currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                firebaseAuth.signOut();
                                Toast.makeText(getApplicationContext(), "Conta Apagada com sucesso", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else System.out.println("Erro");
            }
        });


//        assert currentUser != null;
//        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    firebaseAuth.signOut();
//                    Toast.makeText(getApplicationContext(), "Conta Apagada com sucesso", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                    FirebaseFirestore.getInstance().collection("empresa")
//                            .document(currentUser.getUid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                System.out.println("OK");
//                            }else System.out.println("Erro");
//                        }
//                    });
//                }else Toast.makeText(getApplicationContext(), "Erro ao Apagar", Toast.LENGTH_LONG).show();
//            }
//        });
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

    private void footerNavigation() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEmpresa.this, HomeEmpresa.class);
                startActivity(intent);
            }
        });
        nav_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListarCategoria.class));
            }
        });
    }

    private void inicarCompoenente() {
        img_home = findViewById(R.id.img_home);

        btn_sair = findViewById(R.id.btn_sair);
        btn_editar = findViewById(R.id.btm_editar);
        btn_apagar = findViewById(R.id.btn_apagar);
        btn_atualizar_senha = findViewById(R.id.btn_atualizar_senha);

        text_nome_empresa = findViewById(R.id.text_nome_empresa);
        text_nome_proetario = findViewById(R.id.text_nome_proetario);
        text_cpf_proetario = findViewById(R.id.text_cpf_proetario);
        text_categoria = findViewById(R.id.text_categoria);
        text_descricao = findViewById(R.id.text_descricao);
        text_telefone = findViewById(R.id.text_telefone);
        text_email = findViewById(R.id.text_email);

        nav_logo = findViewById(R.id.nav_logo);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = dataBase.collection("empresa").document(uuid);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null) {
                    text_nome_empresa.setText(documentSnapshot.getString("nomeEmpresa"));
                    text_nome_proetario.setText(documentSnapshot.getString("nomeDono"));
                    text_cpf_proetario.setText(documentSnapshot.getString("cpfDono"));
                    text_categoria.setText(documentSnapshot.getString("categoriaPricipal"));
                    text_descricao.setText(documentSnapshot.getString("descricao"));
                    text_telefone.setText(documentSnapshot.getString("telefone"));
                    text_email.setText(email);
                }
            }
        });
    }
}