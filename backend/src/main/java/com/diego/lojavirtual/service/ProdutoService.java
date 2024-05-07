package com.diego.lojavirtual.service;

import com.diego.lojavirtual.dtos.ProdutoDto;
import com.diego.lojavirtual.exceptions.ObjectNotFoundException;
import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.model.MarcaProduto;
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

    @Autowired private CategoriaProdutoService categoriaProdutoService;

    public Produto save(Long idEmpresa, Produto produto) {

        if (!existeProduto(produto.getNome())) {
            PessoaJuridica obj = pessoaPjService.findPjById(idEmpresa);
            CategoriaProduto cat = categoriaProdutoService.findById(produto.getId());

            // validar marca e Nota Item produto

            produto.setId(null);
            produto.setEmpresa(obj);
            produto.setCategoriaProduto(cat);

            return produtoRepository.save(produto);
        }
        return produto;
    }

    public Optional<Produto> findById(Long id) {
        Optional<Produto> obj = produtoRepository.findById(id);
        return Optional.ofNullable(obj.orElseThrow(() -> new ObjectNotFoundException(
                "Produto não encontrado! Id: " + id + ", Tipo: " + CategoriaProduto.class.getName())));
    }

    public List<Produto> findByName(String produtoNome) {
        return produtoRepository.findByName(produtoNome);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public boolean existeProduto(String produtoDesc) {
        if (produtoRepository.existeProduto(produtoDesc)) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException(
                    "O produto informado já existe. O sistema não permite produtos duplicados. Por favor, informe um produto único");
        }

        return false;
    }


    public Object update(Produto produto) {
        Optional<Produto> obj = findById(produto.getId());

        if (obj.isPresent()) {
            ProdutoDto dto = new ProdutoDto(produto);
            return dto;
        }

        return produto;
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
