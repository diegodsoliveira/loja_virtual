package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.model.dto.CategoriaProdutoDTO;
import com.diego.lojavirtual.service.CategoriaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaProdutoController {

    @Autowired private CategoriaProdutoService categoriaProdutoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaProduto> findById(@PathVariable("id") Long id) {
        CategoriaProduto obj = categoriaProdutoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<CategoriaProdutoDTO>> findAll() {
        List<CategoriaProduto> list = categoriaProdutoService.findAll();
        List<CategoriaProdutoDTO> listDTO = list.stream().map(CategoriaProdutoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDTO);
    }


    @PostMapping(value = "**/create/{idEmpresa}")
    public ResponseEntity<CategoriaProdutoDTO> create(@PathVariable("idEmpresa") Long idEmpresa, @RequestBody @Valid CategoriaProduto categoriaProduto) {
        CategoriaProduto newObj = categoriaProdutoService.save(idEmpresa, categoriaProduto);

        return ResponseEntity.ok().body(new CategoriaProdutoDTO(newObj));
    }

    @PutMapping(value = "**/update")
    public ResponseEntity<CategoriaProdutoDTO> update(@RequestBody @Valid CategoriaProduto categoriaProduto) {
        CategoriaProduto newObj = categoriaProdutoService.update(categoriaProduto);

        return ResponseEntity.ok().body(new CategoriaProdutoDTO(newObj));
    }

    @DeleteMapping(value = "**/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id) {
        String nomeCat = categoriaProdutoService.findById(id).getNomeDesc();
        categoriaProdutoService.delete(id);
        return new ResponseEntity<String>("Categoria " + nomeCat + " deletada com sucesso.", HttpStatus.OK);
    }
}
