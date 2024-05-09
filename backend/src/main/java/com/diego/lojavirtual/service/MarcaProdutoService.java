package com.diego.lojavirtual.service;

import com.diego.lojavirtual.exceptions.ObjectNotFoundException;
import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.model.MarcaProduto;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.repository.MarcaProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaProdutoService {

    @Autowired
    private PessoaService pessoaPjService;

    @Autowired
    private MarcaProdutoRepository marcaProdutoRepository;

    public MarcaProduto save(Long idEmpresa, MarcaProduto marcaProduto) {

        if (!existeMarca(marcaProduto.getNomeDesc())) {
            PessoaJuridica obj = pessoaPjService.findPjById(idEmpresa);
            marcaProduto.setId(null);
            marcaProduto.setEmpresa(obj);

            return marcaProdutoRepository.save(marcaProduto);
        }
        return marcaProduto;
    }

    public MarcaProduto findById(Long id) {
        Optional<MarcaProduto> obj = marcaProdutoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Marca não encontrada! Id: " + id + ", Tipo: " + CategoriaProduto.class.getName()));
    }

    public List<MarcaProduto> findMarcaByDesc(String marcaDesc) {
        return marcaProdutoRepository.findMarcaByDesc(marcaDesc.toUpperCase());
    }

    public List<MarcaProduto> findAll() {
        return marcaProdutoRepository.findAll();
    }

    public boolean existeMarca(String nomeDesc) {
        if (marcaProdutoRepository.existeMarca(nomeDesc)) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException("A marca informada já existe. O sistema não permite marcas duplicadas. Por favor, informe uma marca única");
        }

        return false;
    }


    public MarcaProduto update(MarcaProduto marcaProduto) {
        MarcaProduto obj = findById(marcaProduto.getId());
        obj.setNomeDesc(marcaProduto.getNomeDesc());
        return marcaProdutoRepository.save(obj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            marcaProdutoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new com.diego.lojavirtual.exceptions.DataIntegrityViolationException(
                    "Marca não pôde ser deletada.", e.getCause());
        }
    }
}
