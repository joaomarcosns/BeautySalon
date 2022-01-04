package com.joaomarcos.beautysalon.objeto;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class Favoritos {
    @Exclude
    private String id;
    private String uIdCliente;
    private String uIdEmpresa;

    public Favoritos() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuIdCliente() {
        return uIdCliente;
    }

    public void setuIdCliente(String uIdCliente) {
        this.uIdCliente = uIdCliente;
    }

    public String getuIdEmpresa() {
        return uIdEmpresa;
    }

    public void setuIdEmpresa(String uIdEmpresa) {
        this.uIdEmpresa = uIdEmpresa;
    }

    @NonNull
    @Override
    public String toString() {
        return "Favoritos{" +
                "id='" + id + '\'' +
                ", uIdCliente='" + uIdCliente + '\'' +
                ", uIdEmpresa='" + uIdEmpresa + '\'' +
                '}';
    }
}
