package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where u.login = ?1")
    Usuario findUserByLogin(String login);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "update usuario set token = ?1 where login = ?2")
    void atualizaTokenUser(String token, String login);

    @Query(value = "select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
    Usuario findUserByPessoa(Long id, String email);

    @Query(value = "select CONSTRAINT_name from information_schema.constraint_column_usage\n" +
            "\twhere table_name = 'usuario_acesso' and COLUMN_name = 'acesso_id' and\n" +
            "\tconstraint_name <> 'unique_acesso_user';", nativeQuery = true)
    String consultaConstraintRole();

    @Transactional
    @Modifying
    @Query(value = "insert into usuario_acesso(usuario_id, acesso_id) values (?1, (select id from acesso where descricao = ?2));", nativeQuery = true)
    void insereAcessoUsuario(Long id, String acesso);

    @Query(value = "select u from Usuario u where u.dataAtualSenha <= current_date - 90")
    List<Usuario> verificaUsuarioSenhaVencida();
}
