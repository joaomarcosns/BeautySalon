package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.joaomarcos.beautysalon.R;

public class ProfileCliente extends AppCompatActivity {

    private ImageView img_home;
    private ImageView img_search;
    private ImageView img_love;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_cliente);
        inicarComponente();
        footerNavigation();

    }

    private void inicarComponente() {
        img_home = findViewById(R.id.img_home);
        img_search = findViewById(R.id.img_search);
        img_love = findViewById(R.id.img_love);
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
}