package com.diego.lojavirtual.controller;

import com.diego.lojavirtual.model.Acesso;
import com.diego.lojavirtual.model.Produto;
import com.diego.lojavirtual.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @ResponseBody
    @PostMapping(value = "**/salvarAcesso")
    public ResponseEntity<?> save(@RequestBody Produto produto) {

        if (produto.getId() == null) {
            List<Produto> produtos = produtoService.findByName(produto.getNome());

            if (!produtos.isEmpty()) {
                return ResponseEntity.badRequest().body("Produto já existe. O sistema não permite produtos repetidos. Insira um produto único");
            }
        }

        Produto acessoSalvo = produtoService.save(produto);
        return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
    }

    @GetMapping(value = "**/buscarPorDesc/{desc}")
    public ResponseEntity<List<Produto>> buscarPorDesc(@PathVariable("desc") String desc) {

        List<Produto> acessos = produtoService.buscarAcessoDesc(desc.toUpperCase());

        return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
    }

    @GetMapping(value = "**/buscarPorId/{id}")
    public ResponseEntity<Produto> findById(@PathVariable("id") Long idAcesso) {

        Produto acesso = produtoService.findById(idAcesso).get();

        return new ResponseEntity<Acesso>(acesso,HttpStatus.OK);
    }

    @GetMapping(value = "*/obterAcesso/{id}")
    public ResponseEntity<Produto> findAccessById(@PathVariable("id") Long idAcesso) {

        return produtoService.findById(idAcesso)
                .map(acesso -> ResponseEntity.ok().body(new Acesso(acesso)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado."));

    }

    @DeleteMapping("**/deleteAcesso")
    public ResponseEntity<String> delete(@RequestBody Acesso acesso) {
        Optional<Produto> optional = produtoService.findById(acesso.getId());

        if (optional.isPresent()) {
            produtoService.deleteById(acesso.getId());
            return ResponseEntity.ok().body("Acesso " + optional.get().getDescricao() + " deletado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Acesso não encontrado.");
        }
    }

}
