package com.vinicius.AdotaPet.model;

import java.time.LocalDate;

// Representa a tabela Vacinacao(id_animal, id_vacina, data_aplicacao) do modelo relacional:
// o registo de que um determinado Animal tomou uma determinada Vacina numa certa data.
public class Vacinacao {

    private Integer id_animal;
    private Integer id_vacina;
    private LocalDate data_aplicacao;

    // Campos extra preenchidos apenas nas consultas (join), para facilitar a exibição no Frontend
    private String nome_vacina;
    private String fabricante;

    public Vacinacao() {
    }

    public Vacinacao(Integer id_animal, Integer id_vacina, LocalDate data_aplicacao) {
        this.id_animal = id_animal;
        this.id_vacina = id_vacina;
        this.data_aplicacao = data_aplicacao;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public Integer getId_vacina() {
        return id_vacina;
    }

    public void setId_vacina(Integer id_vacina) {
        this.id_vacina = id_vacina;
    }

    public LocalDate getData_aplicacao() {
        return data_aplicacao;
    }

    public void setData_aplicacao(LocalDate data_aplicacao) {
        this.data_aplicacao = data_aplicacao;
    }

    public String getNome_vacina() {
        return nome_vacina;
    }

    public void setNome_vacina(String nome_vacina) {
        this.nome_vacina = nome_vacina;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
