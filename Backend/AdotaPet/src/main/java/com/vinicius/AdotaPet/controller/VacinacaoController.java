package com.vinicius.AdotaPet.controller;

import com.vinicius.AdotaPet.dao.VacinacaoDAO;
import com.vinicius.AdotaPet.model.Vacinacao;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacinacoes")
public class VacinacaoController {

    private final VacinacaoDAO vacinacaoDAO;

    public VacinacaoController(VacinacaoDAO vacinacaoDAO) {
        this.vacinacaoDAO = vacinacaoDAO;
    }

    // Rota GET: http://localhost:8080/api/vacinacoes/animal/{id_animal}
    // Retorna o histórico de vacinas de um animal específico
    @GetMapping("/animal/{idAnimal}")
    public List<Vacinacao> listarPorAnimal(@PathVariable Integer idAnimal) {
        return vacinacaoDAO.listarPorAnimal(idAnimal);
    }

    // Rota POST: http://localhost:8080/api/vacinacoes
    // Registra que um animal tomou uma vacina numa determinada data
    @PostMapping
    public String registrar(@RequestBody Vacinacao vacinacao) {
        vacinacaoDAO.registrar(vacinacao);
        return "Vacinação registrada com sucesso!";
    }
}
