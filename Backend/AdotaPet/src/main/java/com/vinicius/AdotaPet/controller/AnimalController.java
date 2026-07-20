package com.vinicius.AdotaPet.controller;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
    public List<Animal> listarTodos(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.isBlank()) {
            return animalDAO.buscarPorNome(nome);
        }
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
    public ResponseEntity<String> deletar(@PathVariable Integer id) {
        try {
            animalDAO.deletar(id);
            return ResponseEntity.ok("Animal removido com sucesso!");
        } catch (DataIntegrityViolationException e) {
            // Acontece quando o animal já tem adoções ou vacinações associadas
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Não é possível remover: este animal já tem adoções ou vacinações registadas.");
        }
    } }