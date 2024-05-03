package com.diego.lojavirtual.service;

import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.repository.CategoriaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaProdutoService {

    @Autowired private CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProduto save(CategoriaProduto categoriaProduto) {
        categoriaProduto.setId(null);
        return categoriaProdutoRepository.save(categoriaProduto);
    }
}
