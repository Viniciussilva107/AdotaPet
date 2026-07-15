package com.vinicius.AdotaPet.controller;
import com.vinicius.AdotaPet.dao.AnimalDAO;
import com.vinicius.AdotaPet.model.Animal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
@CrossOrigin(origins = "http://localhost:5173") // Permite que o React (na porta 5173) aceda a esta API sem erros de CORS
public class AnimalController {

    private final AnimalDAO animalDAO;

    // O Spring Boot injeta o seu AnimalDAO automaticamente aqui através do construtor
    public AnimalController(AnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
    }

    // Rota GET: http://localhost:8080/api/animais
    // Retorna a lista de todos os animais em formato JSON para o Front-end
    @GetMapping
    public List<Animal> listarTodos() {
        return animalDAO.listarTodos();
    }

    // Rota POST: http://localhost:8080/api/animais
    // Recebe os dados de um novo animal enviados pelo formulário do React e guarda na base de dados
    @PostMapping
    public String salvar(@RequestBody Animal animal) {
        animalDAO.salvar(animal);
        return "Animal cadastrado com sucesso!";
    }
}