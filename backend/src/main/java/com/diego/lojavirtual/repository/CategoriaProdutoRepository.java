package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {

    @Query(nativeQuery = true, value = "select * from categoria_produto where upper(trim(nome_desc)) = ?1;")
    boolean existeCategoria(String nomeCategoria);

    @Query(value = "select cp from CategoriaProduto cp where upper(trim(nome_desc)) like %?1%")
    CategoriaProduto findByDesc(String catDesc);
}
