package com.diego.lojavirtual.service;

import com.diego.lojavirtual.exceptions.ObjectNotFoundException;
import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.repository.CategoriaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaProdutoService {

    @Autowired
    private PessoaService pessoaPjService;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProduto save(Long idEmpresa, CategoriaProduto categoriaProduto) {

        if (!existeCategoria(categoriaProduto.getNomeDesc())) {
            PessoaJuridica obj = pessoaPjService.findPjById(idEmpresa);
            categoriaProduto.setId(null);
            categoriaProduto.setEmpresa(obj);

            return categoriaProdutoRepository.save(categoriaProduto);
        }
        return categoriaProduto;
    }

    public CategoriaProduto findById(Long id) {
        Optional<CategoriaProduto> obj = categoriaProdutoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada! Id: " + id + ", Tipo: " + CategoriaProduto.class.getName()));
    }

    public CategoriaProduto findByDesc(String catDesc) {
        return categoriaProdutoRepository.findByDesc(catDesc);
    }

    public List<CategoriaProduto> findAll() {
        return categoriaProdutoRepository.findAll();
    }

    public boolean existeCategoria(String catDesc) {
        if (categoriaProdutoRepository.existeCategoria(catDesc)) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException("A categoria informada já existe. O sistema não permite categorias duplicadas. Por favor, informe uma categoria única");
        }

        return false;
    }


    public CategoriaProduto update(CategoriaProduto categoriaProduto) {
        CategoriaProduto obj = findById(categoriaProduto.getId());
        obj.setNomeDesc(categoriaProduto.getNomeDesc());
        return categoriaProdutoRepository.save(obj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            categoriaProdutoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException(
                    "Categoria não pôde ser deletada.", e.getCause());
        }
    }
}
