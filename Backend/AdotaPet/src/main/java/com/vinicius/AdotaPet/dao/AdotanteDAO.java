package com.vinicius.AdotaPet.dao;

import com.vinicius.AdotaPet.model.Adotante;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Adotante> listarTodos() {
        String sql = "SELECT * FROM Adotante";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adotante.class));
    }

    public Adotante buscarPorCpf(String cpf) {
        String sql = "SELECT * FROM Adotante WHERE cpf = ?";
        List<Adotante> resultado = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Adotante.class), cpf);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void atualizar(Adotante adotante) {
        String sql = "UPDATE Adotante SET nome = ?, celular = ? WHERE cpf = ?";
        jdbcTemplate.update(sql, adotante.getNome(), adotante.getCelular(), adotante.getCpf());
    }

    public void deletar(String cpf) {
        String sql = "DELETE FROM Adotante WHERE cpf = ?";
        jdbcTemplate.update(sql, cpf);
    }
}