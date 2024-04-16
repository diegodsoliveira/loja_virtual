package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.repository.AcessoRepository;
import com.diego.lojavirtual.service.AcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@Transactional
@RestController
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @Autowired
    private AcessoRepository acessoRepository;

    @ResponseBody
    @PostMapping(value = "**/salvarAcesso")
    public ResponseEntity<Acesso> save(@RequestBody Acesso acesso) {

        Acesso acessoSalvo = acessoService.save(acesso);

        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorDesc/{desc}")
    public ResponseEntity<List<Acesso>> buscarPorDesc(@PathVariable("desc") String desc) {

        List<Acesso> acessos = acessoRepository.buscarAcessoDesc(desc.toUpperCase());

        return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "**/buscarPorId/{id}")
    public ResponseEntity<Acesso> findById(@PathVariable("id") Long idAcesso) {

        Acesso acesso = acessoRepository.findById(idAcesso).get();

        return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping(value = "*/obterAcesso/{id}")
    public ResponseEntity<Acesso> findAccessById(@PathVariable("id") Long idAcesso) {

        return acessoRepository.findById(idAcesso)
                .map(acesso -> ResponseEntity.ok().body(new Acesso(acesso)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado."));

    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable("id") Long idAcesso) {
        Optional<Acesso> acesso = acessoRepository.findById(idAcesso);

        if (acesso.isPresent()) {
            acessoRepository.deleteById(idAcesso);
            return ResponseEntity.ok().body("Acesso " + acesso.get().getDescricao() + " deletado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Acesso não encontrado.");
        }
    }

}
