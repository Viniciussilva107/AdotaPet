package com.vinicius.AdotaPet.dao;

import com.vinicius.AdotaPet.model.Vacinacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VacinacaoDAO {

    private final JdbcTemplate jdbcTemplate;

    public VacinacaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registrar(Vacinacao vacinacao) {
        String sql = "INSERT INTO Vacinacao (id_animal, id_vacina, data_aplicacao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                vacinacao.getId_animal(),
                vacinacao.getId_vacina(),
                vacinacao.getData_aplicacao());
    }

    // Lista o histórico de vacinas de um animal
    public List<Vacinacao> listarPorAnimal(Integer idAnimal) {
        String sql = "SELECT v.id_animal, v.id_vacina, v.data_aplicacao, va.nome AS nome_vacina, va.fabricante " +
                "FROM Vacinacao v " +
                "JOIN Vacina va ON va.id_vacina = v.id_vacina " +
                "WHERE v.id_animal = ? " +
                "ORDER BY v.data_aplicacao DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Vacinacao vacinacao = new Vacinacao();
            vacinacao.setId_animal(rs.getInt("id_animal"));
            vacinacao.setId_vacina(rs.getInt("id_vacina"));
            vacinacao.setData_aplicacao(rs.getDate("data_aplicacao").toLocalDate());
            vacinacao.setNome_vacina(rs.getString("nome_vacina"));
            vacinacao.setFabricante(rs.getString("fabricante"));
            return vacinacao;
        }, idAnimal);
    }
}
