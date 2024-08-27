package com.example.demo.repository;

import com.example.demo.entity.CursoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoUsuarioRepository extends JpaRepository<CursoUsuario, Long> {
}
