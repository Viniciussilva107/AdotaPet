package com.vinicius.AdotaPet.controller;

import com.vinicius.AdotaPet.dao.VacinaDAO;
import com.vinicius.AdotaPet.model.Vacina;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacinas")
public class VacinaController {

    private final VacinaDAO vacinaDAO;

    public VacinaController(VacinaDAO vacinaDAO) {
        this.vacinaDAO = vacinaDAO;
    }

    // Rota GET: http://localhost:8080/api/vacinas
    @GetMapping
    public List<Vacina> listarTodos(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.isBlank()) {
            return vacinaDAO.buscarPorNome(nome);
        }
        return vacinaDAO.listarTodos();
    }

    // Rota GET: http://localhost:8080/api/vacinas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Vacina> buscarPorId(@PathVariable Integer id) {
        Vacina vacina = vacinaDAO.buscarPorId(id);
        if (vacina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vacina);
    }

    // Rota POST: http://localhost:8080/api/vacinas
    @PostMapping
    public String cadastrar(@RequestBody Vacina vacina) {
        vacinaDAO.salvar(vacina);
        return "Vacina cadastrada com sucesso!";
    }

    // Rota PUT: http://localhost:8080/api/vacinas/{id}
    @PutMapping("/{id}")
    public String atualizar(@PathVariable Integer id, @RequestBody Vacina vacina) {
        vacina.setId_vacina(id);
        vacinaDAO.atualizar(vacina);
        return "Vacina atualizada com sucesso!";
    }

    // Rota DELETE: http://localhost:8080/api/vacinas/{id}
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id) {
        vacinaDAO.deletar(id);
        return "Vacina removida com sucesso!";
    }
}
