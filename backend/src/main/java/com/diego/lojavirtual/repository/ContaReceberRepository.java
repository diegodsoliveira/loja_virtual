package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.ContaReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContaReceberRepository extends JpaRepository<ContaReceber, Long> {
}
