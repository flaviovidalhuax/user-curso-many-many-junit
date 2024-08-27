package com.example.demo.repository;

import com.example.demo.entity.Seguro;
import com.example.demo.entity.SeguroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeguroUsuarioRepository extends JpaRepository<SeguroUsuario, Long> {

}
