package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.joaomarcos.beautysalon.R;

public class Search extends AppCompatActivity {

    private ImageView img_home;
    private ImageView img_perfil;
    private ImageView img_love;
    private EditText edit_search;

    private ImageView img_manicure;
    private ImageView img_pedicure;
    private ImageView img_cabeleireira;
    private ImageView img_massagem;
    private ImageView img_maquiagem;
    private ImageView img_depilacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        inicarComponente();
        footerNavigation();
        imgPesquisa();
    }


    private void inicarComponente() {
        img_home = findViewById(R.id.img_home);
        img_perfil = findViewById(R.id.img_user);
        img_love = findViewById(R.id.img_love);
        edit_search= findViewById(R.id.edit_search);

        img_manicure = findViewById(R.id.img_manicure);
        img_pedicure = findViewById(R.id.img_pedicure);
        img_cabeleireira = findViewById(R.id.img_cabeleireira);
        img_massagem = findViewById(R.id.img_massagem);
        img_maquiagem = findViewById(R.id.img_maquiagem);
        img_depilacao = findViewById(R.id.img_depilacao);
    }

    private void footerNavigation() {
        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, ProfileCliente.class);
                startActivity(intent);
            }
        });

        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, HomeCliente.class);
                startActivity(intent);
            }
        });

        img_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, LoveEmpresa.class);
                startActivity(intent);
            }
        });
    }

    private void imgPesquisa() {
        img_manicure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search.this, "MANICURE", Toast.LENGTH_SHORT).show();
                pesquisaManicure();
            }
        });

        img_pedicure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search.this, "PEDICURE", Toast.LENGTH_SHORT).show();
                pesquisaPedicure();
            }
        });

        img_cabeleireira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search.this, "CABELELEIRO", Toast.LENGTH_SHORT).show();
                pesquisaCabeleireira();
            }
        });

        img_massagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search.this, "MASSAGEM", Toast.LENGTH_SHORT).show();
                pesquisaMassagem();
            }
        });

        img_maquiagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search.this, "MAQUIAGEM", Toast.LENGTH_SHORT).show();
                pesquisaMaquiagem();
            }
        });

        img_depilacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Search.this, "DEPILACAO", Toast.LENGTH_SHORT).show();
                pesquisaDepilacao();
            }
        });
    }

    private void pesquisaDepilacao() {
    }

    private void pesquisaMaquiagem() {
    }

    private void pesquisaMassagem() {
    }

    private void pesquisaCabeleireira() {
    }

    private void pesquisaPedicure() {
    }

    private void pesquisaManicure() {
    }
}