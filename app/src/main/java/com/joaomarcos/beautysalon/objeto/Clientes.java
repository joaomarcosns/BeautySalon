package com.joaomarcos.beautysalon.objeto;

import com.google.firebase.firestore.Exclude;

public class Clientes {
    @Exclude
    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private Integer nivelAcesso;

    public Clientes() {
    }

    public Clientes(String nome, String cpf, String telefone, String email, Integer nivelAcesso) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.nivelAcesso = nivelAcesso;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(Integer nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
