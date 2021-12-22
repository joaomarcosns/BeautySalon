package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.joaomarcos.beautysalon.R;

public class ProfileEmpresa extends AppCompatActivity {

    private ImageView img_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_empresa);
        inicarCompoenente();
        footerNavigation();
    }

    private void footerNavigation() {
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileEmpresa.this, HomeEmpresa.class);
                startActivity(intent);
            }
        });
    }

    private void inicarCompoenente() {
        img_home = findViewById(R.id.img_home);
    }
}