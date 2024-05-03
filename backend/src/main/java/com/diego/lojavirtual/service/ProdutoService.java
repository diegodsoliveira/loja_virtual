package com.diego.lojavirtual.service;

import com.diego.lojavirtual.exceptions.ObjectNotFoundException;
import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.model.Produto;
import com.diego.lojavirtual.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private PessoaService pessoaPjService;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto save(Long idEmpresa, Produto produto) {

        if (!existeCategoria(produto.getNome())) {
            PessoaJuridica obj = pessoaPjService.findPjById(idEmpresa);
            produto.setId(null);
            produto.setEmpresa(obj);

            return produtoRepository.save(produto);
        }
        return produto;
    }

    public Produto findById(Long id) {
        Optional<Produto> obj = produtoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + CategoriaProduto.class.getName()));
    }

    public Produto findByDesc(String produtoDesc) {
        return produtoRepository.findByName(produtoDesc);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public boolean existeCategoria(String produtoDesc) {
        if (produtoRepository.existeProduto(produtoDesc)) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException(
                    "O produto informado já existe. O sistema não permite produtos duplicados. Por favor, informe um produto único");
        }

        return false;
    }


    public Produto update(Produto produto) {
        Produto obj = findById(produto.getId());
        obj.setNomeDesc(produto.getNomeDesc());
        return produtoRepository.save(obj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException(
                    "Categoria não pôde ser deletada.", e.getCause());
        }
    }
}
