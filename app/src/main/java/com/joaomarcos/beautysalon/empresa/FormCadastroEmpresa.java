package com.joaomarcos.beautysalon.empresa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.clientes.HomeCliente;
import com.joaomarcos.beautysalon.objeto.Categoria;
import com.joaomarcos.beautysalon.objeto.Empresas;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormCadastroEmpresa extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText edit_nome_empresa;
    private EditText edit_nome_proetario;
    private EditText edit_cpf_proetario;
    private EditText edit_telefone;
    private EditText edit_descricao;
    private Spinner dropdown;
    private EditText edit_email;
    private EditText edit_senha;
    private EditText edit_comfirma_senha;

    private Button btn_login;
    private ProgressBar progressBar_login;

    String categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro_empresa);
        iniciarComponente();
        maskEditInput();
        scrollViewDescricao();
        dropdown();
        validarCampos();
    }

    private void validarCampos() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeEmpresa = edit_nome_empresa.getText().toString();
                String nomeProprietario = edit_nome_proetario.getText().toString();
                String cpfProprietario = edit_cpf_proetario.getText().toString();
                String telefoneEmpresa = edit_telefone.getText().toString();
                String descricao = edit_descricao.getText().toString();
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();
                String comfirmarSenha = edit_comfirma_senha.getText().toString();

                Pattern pattern = Pattern.compile("[0-9]");
                Matcher SlNomeEmpra = pattern.matcher(nomeEmpresa);
                Matcher SlProorietario = pattern.matcher(nomeProprietario);

                if (nomeEmpresa.isEmpty() || nomeProprietario.isEmpty() || cpfProprietario.isEmpty()
                        || telefoneEmpresa.isEmpty() || descricao.isEmpty() || email.isEmpty()
                        || senha.isEmpty() || comfirmarSenha.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(v, "Preencha todos os campos", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else if (!senha.equals(comfirmarSenha)) {
                    Snackbar snackbar = Snackbar.make(v, "As senhas não batem", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else if (SlNomeEmpra.find()) {
                    Snackbar snackbar = Snackbar.make(v, "Somente letras", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                } else if (SlProorietario.find()) {
                    Snackbar snackbar = Snackbar.make(v, "Somente letras", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else {
                    cadastrarEmpresa(v);
                }
            }
        });
    }

    private void cadastrarEmpresa(View v) {
        String nomeEmpresa = edit_nome_empresa.getText().toString();
        String nomeProprietario = edit_nome_proetario.getText().toString();
        String cpfProprietario = edit_cpf_proetario.getText().toString();
        String telefoneEmpresa = edit_telefone.getText().toString();
        String descricao = edit_descricao.getText().toString();
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();

        Empresas empresa = new Empresas();
        empresa.setNomeEmpresa(nomeEmpresa);
        empresa.setNomeDono(nomeProprietario);
        empresa.setCpfDono(cpfProprietario);
        empresa.setTelefone(telefoneEmpresa);
        empresa.setDescricao(descricao);
        empresa.setCategoriaPricipal(categoria);
        empresa.setNivelAcesso(2);
        Categoria categorias = new Categoria();

        Task<AuthResult> authResultTask = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = Objects.requireNonNull(authResultTask.getResult()).getUser();
                    empresa.setId(user.getUid());
                    FirebaseFirestore.getInstance().collection("empresa").document(Objects.requireNonNull(user).getUid())
                            .set(empresa).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Snackbar snackbar = Snackbar.make(v, "Cadastro relaizado com sucesso", Snackbar.LENGTH_LONG);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();

                            Intent intent = new Intent(getApplicationContext(), HomeEmpresa.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Teste", e.getMessage());
                        }
                    });
                } else {
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
                Log.d("Teste", e.getMessage());
            }
        });



    }

    private void iniciarComponente() {
        edit_nome_empresa = findViewById(R.id.edit_nome_empresa);
        edit_nome_proetario = findViewById(R.id.edit_nome_proetario);
        edit_cpf_proetario = findViewById(R.id.edit_cpf_proetario);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        edit_comfirma_senha = findViewById(R.id.edit_comfirma_senha);
        btn_login = findViewById(R.id.btn_login);
        progressBar_login = findViewById(R.id.progressBar_login);
        edit_telefone = findViewById(R.id.edit_telefone);
        edit_descricao = findViewById(R.id.edit_descricao);
        dropdown = findViewById(R.id.spinner1);
    }

    private void maskEditInput() {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(edit_telefone, smfTelefone);
        edit_telefone.addTextChangedListener(mtwTelefone);

        SimpleMaskFormatter smfCpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtwCpf = new MaskTextWatcher(edit_cpf_proetario, smfCpf);
        edit_cpf_proetario.addTextChangedListener(mtwCpf);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void scrollViewDescricao() {
        edit_descricao.setOnTouchListener(new View.OnTouchListener() {
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

    private void dropdown() {
        String[] items = new String[]{
            "Selecione um categoria", "Manicure Pedicure",
            "Depilação", "Maquiagem", "Limpeza de Pele",
            "Massagem", "Cabeleireiro"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_drop_down, items);
        dropdown.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.item_drop_down);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String item = parent.getItemAtPosition(position).toString();
                    categoria = item;
                    Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}