package com.joaomarcos.beautysalon.objeto;

import com.google.firebase.firestore.Exclude;

public class Categoria {

    @Exclude
    private String id;
    private String nome;
    private String uidEmpresa;

    public Categoria(){}

    public Categoria(String nome, String uidEmpresa) {
        this.nome = nome;
        this.uidEmpresa = uidEmpresa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUidEmpresa() {
        return uidEmpresa;
    }

    public void setUidEmpresa(String uidEmpresa) {
        this.uidEmpresa = uidEmpresa;
    }
}
