package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.AvaliacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
}
