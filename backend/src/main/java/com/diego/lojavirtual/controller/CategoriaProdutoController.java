package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.repository.CategoriaProdutoRepository;
import com.diego.lojavirtual.service.CategoriaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CategoriaProdutoController {

    @Autowired private CategoriaProdutoService categoriaProdutoService;

    @ResponseBody
    @PostMapping(value = "**/categoria/create")
    public ResponseEntity<CategoriaProduto> create(@RequestBody @Valid CategoriaProduto categoriaProduto) {

        return new ResponseEntity<>(categoriaProdutoService.save(categoriaProduto), HttpStatus.OK);
    }
}
