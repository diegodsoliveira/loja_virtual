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
    @PostMapping(value = "**/save")
    public ResponseEntity<?> save(@RequestBody Produto produto) {

        if (produto.getId() == null) {
            List<Produto> produtos = produtoService.findByName(produto.getNome());

            if (!produtos.isEmpty()) {
                return ResponseEntity.badRequest().body("Produto já existe. O sistema não permite produtos repetidos. Insira um produto único");
            }
        }

        Produto obj = produtoService.save(produto.getEmpresa().getId(), produto);
        return new ResponseEntity<Produto>(obj, HttpStatus.OK);
    }

    @GetMapping(value = "**/findByName/{name}")
    public ResponseEntity<List<Produto>> buscarPorDesc(@PathVariable("name") String name) {

        List<Produto> produtos = produtoService.findByName(name.toUpperCase());

        return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
    }

    @GetMapping(value = "**/findById/{id}")
    public ResponseEntity<Produto> findById(@PathVariable("id") Long idProduto) {

        Produto produto = produtoService.findById(idProduto).get();

        return new ResponseEntity<Produto>(produto,HttpStatus.OK);
    }

    @DeleteMapping("**/deleteProdutoById")
    public ResponseEntity<String> delete(@RequestBody Produto produto) {
        Optional<Produto> optional = produtoService.findById(produto.getId());

        if (optional.isPresent()) {
            produtoService.delete(produto.getId());
            return ResponseEntity.ok().body("Produto " + optional.get().getNome() + " deletado com sucesso.");
        } else {
            return ResponseEntity.badRequest().body("Produto não encontrado.");
        }
    }

}
