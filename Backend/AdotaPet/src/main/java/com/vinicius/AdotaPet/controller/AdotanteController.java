package com.vinicius.AdotaPet.controller;

import com.vinicius.AdotaPet.dao.AdotanteDAO;
import com.vinicius.AdotaPet.model.Adotante;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adotantes")
public class AdotanteController {

    private final AdotanteDAO adotanteDAO;

    public AdotanteController(AdotanteDAO adotanteDAO) {
        this.adotanteDAO = adotanteDAO;
    }

    @PostMapping
    public String cadastrarAdotante(@RequestBody Adotante adotante) {
        adotanteDAO.salvar(adotante);
        return "Adotante cadastrado com sucesso!";
    }
}