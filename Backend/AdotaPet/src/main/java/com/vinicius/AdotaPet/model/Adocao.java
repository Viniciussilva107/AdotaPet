package com.vinicius.AdotaPet.model;
import java.time.LocalDate;

public class Adocao {

    private Integer id_adocao;
    private LocalDate data_adocao;
    private Integer id_animal;
    private String cpf_adotante;

    public Adocao() {}

    public Adocao(LocalDate data_adocao, Integer id_animal, String cpf_adotante) {
        this.data_adocao = data_adocao;
        this.id_animal = id_animal;
        this.cpf_adotante = cpf_adotante;
    }

    public Integer getId_adocao() {
        return id_adocao;
    }

    public void setId_adocao(Integer id_adocao) {
        this.id_adocao = id_adocao;
    }

    public LocalDate getData_adocao() {
        return data_adocao;
    }

    public void setData_adocao(LocalDate data_adocao) {
        this.data_adocao = data_adocao;
    }

    public Integer getId_animal() {
        return id_animal;
    }

    public void setId_animal(Integer id_animal) {
        this.id_animal = id_animal;
    }

    public String getCpf_adotante() {
        return cpf_adotante;
    }

    public void setCpf_adotante(String cpf_adotante) {
        this.cpf_adotante = cpf_adotante;
    }
}
