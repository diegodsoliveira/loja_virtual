package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.MarcaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {

    @Query(value = "select mp from MarcaProduto mp where upper(trim(mp.nomeDesc)) like %?1%")
    List<MarcaProduto> findMarcaByDesc(String desc);

    @Query(nativeQuery = true, value = "select count(1) > 0 from marca_produto where upper(trim(nome_desc)) = upper(trim(?1));")
    boolean existeMarca(String descMarca);

}
