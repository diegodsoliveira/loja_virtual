package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.CupomDesconto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CupomDescontoRepository extends JpaRepository<CupomDesconto, Long> {
}
