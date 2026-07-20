package com.vinicius.AdotaPet.controller;

import com.vinicius.AdotaPet.dao.AdotanteDAO;
import com.vinicius.AdotaPet.model.Adotante;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adotantes")
public class AdotanteController {

    private final AdotanteDAO adotanteDAO;

    public AdotanteController(AdotanteDAO adotanteDAO) {
        this.adotanteDAO = adotanteDAO;
    }

    @GetMapping
    public List<Adotante> listarTodos(@RequestParam(required = false) String nome) {
        if (nome != null && !nome.isBlank()) {
            return adotanteDAO.buscarPorNome(nome);
        }
        return adotanteDAO.listarTodos();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Adotante> buscarPorCpf(@PathVariable String cpf) {
        Adotante adotante = adotanteDAO.buscarPorCpf(cpf);
        if (adotante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(adotante);
    }

    @PostMapping
    public String cadastrarAdotante(@RequestBody Adotante adotante) {
        adotanteDAO.salvar(adotante);
        return "Adotante cadastrado com sucesso!";
    }

    @PutMapping("/{cpf}")
    public String atualizar(@PathVariable String cpf, @RequestBody Adotante adotante) {
        adotante.setCpf(cpf);
        adotanteDAO.atualizar(adotante);
        return "Adotante atualizado com sucesso!";
    }

    @DeleteMapping("/{cpf}")
    public String deletar(@PathVariable String cpf) {
        adotanteDAO.deletar(cpf);
        return "Adotante removido com sucesso!";
    }
}