package com.vinicius.AdotaPet.model;

public class Animal {

    private Integer id_animal;
    private boolean status_animal;
    private String nome;
    private String raca;
    private String porte;

    public Animal (){

    }

    public Animal(String nome, String raca, String porte) {
        this.nome = nome;
        this.raca = raca;
        this.porte = porte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public boolean isStatus_animal() {
        return status_animal;
    }

    public void setStatus_animal(boolean status_animal) {
        this.status_animal = status_animal;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }
}

