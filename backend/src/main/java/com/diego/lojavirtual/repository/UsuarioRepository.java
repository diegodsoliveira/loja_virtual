package com.diego.lojavirtual.repository;

import com.diego.lojavirtual.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
