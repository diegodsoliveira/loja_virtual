package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.repository.AcessoRepository;
import com.diego.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarAcesso")
    public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso obj) {

        Acesso acessoSalvo = acessoService.save(obj);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) {

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc);

        return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorId/{id}")
    public ResponseEntity<Acesso> buscarPorId(@PathVariable("id") Long id) {

        Acesso acesso = acessoRepository.findById(id).get();

        return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
    }

}
