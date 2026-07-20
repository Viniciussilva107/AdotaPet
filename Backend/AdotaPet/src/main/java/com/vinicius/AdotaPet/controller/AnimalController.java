package com.vinicius.AdotaPet.controller;
import com.vinicius.AdotaPet.dao.AnimalDAO;
import com.vinicius.AdotaPet.model.Animal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
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

    // Rota GET: http://localhost:8080/api/animais/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Animal> buscarPorId(@PathVariable Integer id) {
        Animal animal = animalDAO.buscarPorId(id);
        if (animal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animal);
    }

    // Rota POST: http://localhost:8080/api/animais
    // Recebe os dados de um novo animal enviados pelo formulário do Front-end e guarda na base de dados
    @PostMapping
    public String salvar(@RequestBody Animal animal) {
        animalDAO.salvar(animal);
        return "Animal cadastrado com sucesso!";
    }

    // Rota PUT: http://localhost:8080/api/animais/{id}
    // Atualiza os dados de um animal já existente
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Integer id, @RequestBody Animal animal) {
        animal.setId_animal(id);
        animalDAO.atualizar(animal);
        return "Animal atualizado com sucesso!";
    }

    // Rota DELETE: http://localhost:8080/api/animais/{id}
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id) {
        animalDAO.deletar(id);
        return "Animal removido com sucesso!";
    }
}