package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaomarcos.beautysalon.R;

public class LoveEmpresa extends AppCompatActivity {

    private ImageView img_home;
    private ImageView img_profile;
    private ImageView img_search;

    private FloatingActionButton btnWhatsapp;
    private FloatingActionButton btnLove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_empresa);
        iniciarComponente();
        footNavigation();
        entrarEmContato();
        onLove();
    }

    private void onLove() {
        btnLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Removidos dos seus favoritos", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void iniciarComponente() {
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_profile = findViewById(R.id.img_user);
        btnWhatsapp = findViewById(R.id.btnWhatsapp);
        btnLove = findViewById(R.id.btnLove);
    }
    private void footNavigation() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoveEmpresa.this, HomeCliente.class);
                startActivity(intent);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoveEmpresa.this, Search.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                startActivity(intent);
            }
        });

        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoveEmpresa.this, ProfileCliente.class);
                startActivity(intent);
            }
        });

    }

    private void entrarEmContato() {
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telefone = "+5577991620945";
                String message = "Olá, vim pelo BEAUTY SALON";
                Toast.makeText(LoveEmpresa.this, "Indo ao whatsapp", Toast.LENGTH_SHORT).show();
                startActivity(
                        new Intent(Intent.ACTION_VIEW,
                                Uri.parse(
                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                                                telefone,
                                                message
                                        )
                                )
                        )
                );
            }
        });
    }
}