package com.vinicius.AdotaPet.controller;

import com.vinicius.AdotaPet.dao.AdocaoDAO;
import com.vinicius.AdotaPet.model.Adocao;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adocoes")
public class AdocaoController {

    private final AdocaoDAO adocaoDAO;

    public AdocaoController(AdocaoDAO adocaoDAO) {
        this.adocaoDAO = adocaoDAO;
    }

    @PostMapping
    public String registrarAdocao(@RequestBody Adocao adocao) {
        adocaoDAO.registrarAdocao(adocao);
        return "Adoção realizada e status do animal atualizado!";
    }
}