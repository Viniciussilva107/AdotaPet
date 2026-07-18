package com.vinicius.AdotaPet.model;

public class Adotante {

    private String cpf;
    private String nome;
    private String celular;

    public Adotante(){

    }
    public Adotante(String cpf, String nome, String celular) {
        this.cpf = cpf;
        this.nome = nome;
        this.celular = celular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
