package com.joaomarcos.beautysalon.clientes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joaomarcos.beautysalon.R;
import com.joaomarcos.beautysalon.adapter.ListEmpresa;
import com.joaomarcos.beautysalon.objeto.Empresas;
import com.joaomarcos.beautysalon.objeto.Favoritos;

import java.util.ArrayList;

public class HomeCliente extends AppCompatActivity {

    private ImageView img_perfil;
    private ImageView img_search;
    private ImageView img_love;
    private FloatingActionButton buttonShow;
    private FloatingActionButton btnWhatsapp;
    private FloatingActionButton btnLove;
    private RatingBar rating_bar_indicator;


    private TextView nome_loja;
    private TextView tipo_categoria;
    private TextView descricao;

    private RecyclerView recyclerView;
    ArrayList<Empresas> empresasArrayList;
    ListEmpresa myAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);
        InicarComponente();
        footerNavigation();
//        entrarEmContato();
//        favoritar();

    }


//    private void favoritar() {
//        btnLove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast toast = Toast.makeText(getApplicationContext(), "Adicionado ao meus favoritos", Toast.LENGTH_LONG);
//                toast.show();
//                Favoritos favoritos = new Favoritos();
//
//            }
//        });
//    }

//    private void modal() {
//        buttonShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
//                        HomeCliente.this, R.style.BottomSheetDialogTheme
//                );
//                View bottomSheetView = LayoutInflater.from(getApplicationContext())
//                        .inflate(
//                                R.layout.modal_rating,
//                                (LinearLayout)findViewById(R.id.bootomSheetContainer)
//                        );
//                bottomSheetView.findViewById(R.id.btnAvaliar).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        RatingBar rating_bar_avaliar = v.findViewById(R.id.rating_bar_avaliar);
//                        Log.i("teste", String.valueOf(rating_bar_avaliar.getRating()));
//                        Toast.makeText(HomeCliente.this, "Obrigado pelo seu feedback", Toast.LENGTH_SHORT).show();
//                        bottomSheetDialog.dismiss();
//                    }
//                });
//
//                bottomSheetDialog.setContentView(bottomSheetView);
//                bottomSheetDialog.show();
//            }
//        });
//    }

    private void InicarComponente() {
        img_perfil = findViewById(R.id.img_user);
        img_search = findViewById(R.id.img_search);
        img_love = findViewById(R.id.img_love);

//        btnWhatsapp = findViewById(R.id.btnWhatsapp);
//        btnLove = findViewById(R.id.btnLove);

        nome_loja = findViewById(R.id.nome_loja);
        tipo_categoria = findViewById(R.id.tipo_categoria);
        descricao = findViewById(R.id.descricao);

        recyclerView = findViewById(R.id.id_list_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    private void footerNavigation() {
        img_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeCliente.this, ProfileCliente.class);
                startActivity(intent);
            }
        });

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeCliente.this, Search.class);
                startActivity(intent);
            }
        });

        img_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeCliente.this, LoveEmpresa.class);
                startActivity(intent);
            }
        });
    }

//    private void entrarEmContato() {
//        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String telefone = "+55(77)991620945";
//                String message = "Olá, vim pelo BEAUTY SALON";
//                Toast.makeText(HomeCliente.this, "Indo ao whatsapp", Toast.LENGTH_SHORT).show();
//                startActivity(
//                        new Intent(Intent.ACTION_VIEW,
//                                Uri.parse(
//                                        String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
//                                                telefone,
//                                                message
//                                        )
//                                )
//                        )
//                );
//            }
//        });
//    }
}