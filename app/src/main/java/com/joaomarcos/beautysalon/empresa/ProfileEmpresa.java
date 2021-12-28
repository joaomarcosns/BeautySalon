package com.joaomarcos.beautysalon.empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.joaomarcos.beautysalon.MainActivity;
import com.joaomarcos.beautysalon.R;

import org.w3c.dom.Text;

public class ProfileEmpresa extends AppCompatActivity {

    private ImageView img_home;

    private ExtendedFloatingActionButton btn_sair;
    private ExtendedFloatingActionButton btn_editar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_empresa);
        inicarCompoenente();
        footerNavigation();
        deslogar();
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
    }

    private void inicarCompoenente() {
        img_home = findViewById(R.id.img_home);

        btn_sair = findViewById(R.id.btn_sair);
        btn_editar = findViewById(R.id.btm_editar);
    }
}