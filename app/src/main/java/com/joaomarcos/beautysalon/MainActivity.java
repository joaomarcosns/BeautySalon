package com.joaomarcos.beautysalon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.joaomarcos.beautysalon.clientes.FormLoginCliente;
import com.joaomarcos.beautysalon.empresa.FormLoginEmpresa;

public class MainActivity extends AppCompatActivity {

    private Button btn_empresa;
    private Button btn_cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IniciarComponente();
        navigation();
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
}