package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.CategoriaProduto;
import com.diego.lojavirtual.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    @Query(nativeQuery = true, value = "select count(1) > 0 from produto where upper(trim(nome)) = upper(trim(?1));")
    boolean existeProduto(String nomeProduto);

    @Query(value = "select p from Produto p where upper(trim(nome)) like %?1%")
    List<Produto> findByName(String nome);

}
