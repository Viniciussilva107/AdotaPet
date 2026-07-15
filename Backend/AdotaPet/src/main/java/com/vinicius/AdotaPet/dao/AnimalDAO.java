package com.vinicius.AdotaPet.dao;

import com.vinicius.AdotaPet.model.Animal;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimalDAO {

    private final DataSource dataSource;

    public AnimalDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void salvar(Animal animal) {
        String sql = "INSERT INTO Animal (nome, raca, porte, status_animal) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getRaca());
            stmt.setString(3, animal.getPorte());
            stmt.setBoolean(4, animal.isStatus_animal());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal: " + e.getMessage());
        }
    }

    // Metodo para buscar todos os animais
    public List<Animal> listarTodos() {
        String sql = "SELECT * FROM Animal";
        List<Animal> animais = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Animal animal = new Animal();

                // Usando os setters exatos do seu modelo
                animal.setId_animal(rs.getInt("id_animal"));
                animal.setNome(rs.getString("nome"));
                animal.setRaca(rs.getString("raca"));
                animal.setPorte(rs.getString("porte"));
                animal.setStatus_animal(rs.getBoolean("status_animal"));

                animais.add(animal);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais: " + e.getMessage());
        }

        return animais;
    }
}