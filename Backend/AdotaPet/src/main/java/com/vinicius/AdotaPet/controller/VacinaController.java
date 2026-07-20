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

    @GetMapping
    public List<Vacina> listarTodos(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.isBlank()) {
            return vacinaDAO.buscarPorNome(nome);
        }
        return vacinaDAO.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacina> buscarPorId(@PathVariable Integer id) {
        Vacina vacina = vacinaDAO.buscarPorId(id);
        if (vacina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vacina);
    }

    @PostMapping
    public String cadastrar(@RequestBody Vacina vacina) {
        vacinaDAO.salvar(vacina);
        return "Vacina cadastrada com sucesso!";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Integer id, @RequestBody Vacina vacina) {
        vacina.setId_vacina(id);
        vacinaDAO.atualizar(vacina);
        return "Vacina atualizada com sucesso!";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Integer id) {
        vacinaDAO.deletar(id);
        return "Vacina removida com sucesso!";
    }
}
