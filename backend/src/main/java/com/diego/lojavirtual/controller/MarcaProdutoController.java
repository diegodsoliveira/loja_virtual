package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.dtos.MarcaProdutoDTO;
import com.diego.lojavirtual.model.MarcaProduto;
import com.diego.lojavirtual.service.MarcaProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/marcaProduto")
public class MarcaProdutoController {

    @Autowired private MarcaProdutoService marcaProdutoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<MarcaProduto> findById(@PathVariable("id") Long id) {
        MarcaProduto obj = marcaProdutoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<MarcaProdutoDTO>> findAll() {
        List<MarcaProduto> list = marcaProdutoService.findAll();
        List<MarcaProdutoDTO> listDTO = list.stream().map(MarcaProdutoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/findByDesc/{desc}")
    public ResponseEntity<List<MarcaProdutoDTO>> findByDesc(@PathVariable("desc") String desc) {

        List<MarcaProduto> marcas = marcaProdutoService.findMarcaByDesc(desc);
        List<MarcaProdutoDTO> listDto = marcas.stream().map(MarcaProdutoDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping(value = "**/create/{idEmpresa}")
    public ResponseEntity<MarcaProdutoDTO> create(@PathVariable("idEmpresa") Long idEmpresa, @RequestBody @Valid MarcaProduto marcaProduto) {

        MarcaProduto newObj = marcaProdutoService.save(idEmpresa, marcaProduto);

        return ResponseEntity.ok().body(new MarcaProdutoDTO(newObj));
    }

    @PutMapping(value = "**/update")
    public ResponseEntity<MarcaProdutoDTO> update(@RequestBody @Valid MarcaProduto marcaProduto) {
        MarcaProduto newObj = marcaProdutoService.update(marcaProduto);

        return ResponseEntity.ok().body(new MarcaProdutoDTO(newObj));
    }

    @DeleteMapping(value = "**/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        String nomeCat = marcaProdutoService.findById(id).getNomeDesc();
        marcaProdutoService.delete(id);
        return new ResponseEntity<String>("Categoria " + nomeCat + " deletada com sucesso.", HttpStatus.OK);
    }
}
