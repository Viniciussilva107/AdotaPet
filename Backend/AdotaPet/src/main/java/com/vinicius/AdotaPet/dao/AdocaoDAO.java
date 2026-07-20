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
    public AdocaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Se der erro ele desfaz tod o processo
    @Transactional
    public void registrarAdocao(Adocao adocao) {
        String sqlInsertAdocao = "INSERT INTO Adocao (id_animal, cpf_adotante, data_adocao) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlInsertAdocao,
                adocao.getId_animal(),
                adocao.getCpf_adotante(),
                adocao.getData_adocao());

        String sqlUpdateAnimal = "UPDATE Animal SET status_animal = false WHERE id_animal = ?";
        jdbcTemplate.update(sqlUpdateAnimal, adocao.getId_animal());

        System.out.println("Adoção registrada com sucesso e status do animal atualizado!");
    }

    public List<Adocao> listarTodos() {
        String sql = "SELECT * FROM Adocao";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adocao.class));
    }

    // Busca adoções pelo nome do animal ou do adotante (usado na busca do Frontend)
    public List<Adocao> buscarPorTermo(String termo) {
        String sql = "SELECT ad.* FROM Adocao ad " +
                "JOIN Animal a ON a.id_animal = ad.id_animal " +
                "JOIN Adotante ao ON ao.cpf = ad.cpf_adotante " +
                "WHERE a.nome LIKE ? OR ao.nome LIKE ?";
        String like = "%" + termo + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adocao.class), like, like);
    }

}