package com.joaomarcos.beautysalon.objeto;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Avaliacao implements Serializable {
    @Exclude private String id;
    private Empresas empresas;
    private float avalicao;

    public Avaliacao() {
    }

    public Avaliacao(Empresas empresas, Float avalicao) {
        this.empresas = empresas;
        this.avalicao = avalicao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    public float getAvalicao() {
        return avalicao;
    }

    public void setAvalicao(float avalicao) {
        this.avalicao = avalicao;
    }

    @NonNull
    @Override
    public String toString() {
        return "Avaliacao{" +
                "empresas=" + empresas +
                ", avalicao=" + avalicao +
                '}';
    }
}
