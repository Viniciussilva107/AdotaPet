package com.vinicius.AdotaPet.model;

public class Vacina {

    private Integer id_vacina;
    private String nome;
    private String fabricante;

    public Vacina(){
    }

    public Vacina(String nome, String fabricante) {
        this.nome = nome;
        this.fabricante = fabricante;
    }

    public Integer getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(Integer id_vacina) {
        this.id_vacina = id_vacina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
