package com.vinicius.AdotaPet.dao;

import com.vinicius.AdotaPet.model.Vacina;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class VacinaDAO {

    private final JdbcTemplate jdbcTemplate;

    public VacinaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer salvar(Vacina vacina) {
        String sql = "INSERT INTO Vacina (nome, fabricante) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, vacina.getNome());
            stmt.setString(2, vacina.getFabricante());
            return stmt;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().intValue() : null;
    }

    public List<Vacina> listarTodos() {
        String sql = "SELECT * FROM Vacina";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacina.class));
    }

    public Vacina buscarPorId(Integer id) {
        String sql = "SELECT * FROM Vacina WHERE id_vacina = ?";
        List<Vacina> resultado = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vacina.class), id);
        return resultado.isEmpty() ? null : resultado.get(0);
    }

    public void atualizar(Vacina vacina) {
        String sql = "UPDATE Vacina SET nome = ?, fabricante = ? WHERE id_vacina = ?";
        jdbcTemplate.update(sql, vacina.getNome(), vacina.getFabricante(), vacina.getId_vacina());
    }

    public void deletar(Integer id) {
        String sql = "DELETE FROM Vacina WHERE id_vacina = ?";
        jdbcTemplate.update(sql, id);
    }
}
