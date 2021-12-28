package com.joaomarcos.beautysalon.objeto;

public class Categoria {

    private String nome;
    private String uidEmpresa;

    public Categoria(){}

    public Categoria(String nome, String uidEmpresa) {
        this.nome = nome;
        this.uidEmpresa = uidEmpresa;
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
