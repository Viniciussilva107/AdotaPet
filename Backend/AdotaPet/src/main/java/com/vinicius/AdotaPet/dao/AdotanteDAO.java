package com.vinicius.AdotaPet.dao;

import com.vinicius.AdotaPet.model.Adotante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdotanteDAO {

    private final JdbcTemplate jdbcTemplate;

    public AdotanteDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void salvar(Adotante adotante) {
        String sql = "INSERT INTO Adotante (cpf, nome, celular) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, adotante.getCpf(), adotante.getNome(), adotante.getCelular());
    }
}