package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.joaomarcos.beautysalon.R;

public class HomeEmpresa extends AppCompatActivity {

    private ImageView img_comany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_empresa);
        inicarComponente();
        footerNavigation();
    }

    private void inicarComponente() {
        img_comany = findViewById(R.id.nav_comany);
    }

    private void footerNavigation(){
        img_comany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeEmpresa.this, ProfileEmpresa.class);
                startActivity(intent);
            }
        });
    }

}