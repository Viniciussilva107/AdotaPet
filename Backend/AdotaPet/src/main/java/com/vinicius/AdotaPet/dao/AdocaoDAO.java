package com.vinicius.AdotaPet.dao;

import com.vinicius.AdotaPet.model.Adocao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AdocaoDAO {

    private final JdbcTemplate jdbcTemplate;

    // A injeção de dependência do JdbcTemplate
    public AdocaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // O @Transactional garante que, se der erro em uma etapa, ele desfaz a outra
    @Transactional
    public void registrarAdocao(Adocao adocao) {

        // 1. Inserir o registro na tabela de Adoção
        String sqlInsertAdocao = "INSERT INTO Adocao (id_animal, cpf_adotante, data_adocao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlInsertAdocao,
                adocao.getId_animal(),
                adocao.getCpf_adotante(),
                adocao.getData_adocao());

        // 2. Atualizar o status do Animal (supondo que 'false' signifique adotado/indisponível)
        String sqlUpdateAnimal = "UPDATE Animal SET status_adocao = false WHERE id_animal = ?";
        jdbcTemplate.update(sqlUpdateAnimal, adocao.getId_animal());

        System.out.println("Adoção registrada com sucesso e status do animal atualizado!");
    }

    public List<Adocao> listarTodos() {
        String sql = "SELECT * FROM Adocao";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adocao.class));
    }
}