package com.joaomarcos.beautysalon.objeto;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Empresas implements Serializable {
    @Exclude
    private String id;
    private String nomeEmpresa;
    private String nomeDono;
    private String cpfDono;
    private String descricao;
    private String telefone;
    private String categoriaPricipal;
    private Integer nivelAcesso;

    public Empresas() {
    }

    public Empresas(String nomeEmpresa, String nomeDono, String cpfDono, String descricao, String telefone, String categoriaPricipal, Integer nivelAcesso) {
        this.nomeEmpresa = nomeEmpresa;
        this.nomeDono = nomeDono;
        this.cpfDono = cpfDono;
        this.descricao = descricao;
        this.telefone = telefone;
        this.categoriaPricipal = categoriaPricipal;
        this.nivelAcesso = nivelAcesso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNomeDono() {
        return nomeDono;
    }

    public void setNomeDono(String nomeDono) {
        this.nomeDono = nomeDono;
    }

    public String getCpfDono() {
        return cpfDono;
    }

    public void setCpfDono(String cpfDono) {
        this.cpfDono = cpfDono;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCategoriaPricipal() {
        return categoriaPricipal;
    }

    public void setCategoriaPricipal(String categoriaPricipal) {
        this.categoriaPricipal = categoriaPricipal;
    }

    public Integer getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(Integer nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }

    @Override
    public String toString() {
        return "Empresas{" +
                "id='" + id + '\'' +
                ", nomeEmpresa='" + nomeEmpresa + '\'' +
                ", nomeDono='" + nomeDono + '\'' +
                ", cpfDono='" + cpfDono + '\'' +
                ", descricao='" + descricao + '\'' +
                ", telefone='" + telefone + '\'' +
                ", categoriaPricipal='" + categoriaPricipal + '\'' +
                ", nivelAcesso=" + nivelAcesso +
                '}';
    }
}
