package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    @Query(value = "select pf from PessoaFisica pf where pf.cpf = ?1")
    List<PessoaFisica> existeCpfCadastrado(String cpf);

    @Query(value = "select pf from PessoaFisica pf where pf.email = ?1")
    PessoaFisica existeEmailCadastrado(String email);
}
