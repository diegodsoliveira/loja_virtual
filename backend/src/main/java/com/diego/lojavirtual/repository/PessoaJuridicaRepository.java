package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.PessoaFisica;
import com.diego.lojavirtual.model.PessoaJuridica;
import com.diego.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

    @Query(value = "select pj from PessoaJuridica pj where pj.cnpj = ?1")
    List<PessoaJuridica> existeCnpjCadastrado(String cnpj);

    @Query(value = "select pj from PessoaJuridica pj where pj.inscricaoEstadual = ?1")
    PessoaJuridica existeInscricaoEstadual(String inscricaoEstadual);

    @Query(value = "select pj from PessoaJuridica pj where pj.email = ?1")
    PessoaJuridica existeEmailCadastrado(String email);

    @Query(value = "select pj from PessoaJuridica pj where upper(trim(pj.nome)) like %?1%")
    List<PessoaJuridica> pesquisaPorNomePj(String nome);
}
