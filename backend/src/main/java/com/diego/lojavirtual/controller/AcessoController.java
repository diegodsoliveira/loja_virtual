package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping("/salvarAcesso")
    public Acesso salvarAcesso(Acesso obj) {
        return acessoService.create(obj);
    }
}
