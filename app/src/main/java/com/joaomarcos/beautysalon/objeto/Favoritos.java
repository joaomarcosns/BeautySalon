package com.joaomarcos.beautysalon.objeto;

public class Favoritos {
    private Clientes clientes;
    private Empresas empresas;

    public Favoritos() {
    }

    public Favoritos(Clientes clientes, Empresas empresas) {
        this.clientes = clientes;
        this.empresas = empresas;
    }

    public Clientes getClientes() {
        return clientes;
    }

    public void setClientes(Clientes clientes) {
        this.clientes = clientes;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }
}
