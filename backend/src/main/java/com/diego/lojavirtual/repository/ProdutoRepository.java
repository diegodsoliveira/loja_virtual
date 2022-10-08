package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
