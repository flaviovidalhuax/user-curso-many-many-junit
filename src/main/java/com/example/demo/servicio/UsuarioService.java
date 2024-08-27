package com.example.demo.servicio;

import com.example.demo.entity.Curso;
import com.example.demo.entity.CursoUsuario;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.CursoUsuarioRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    CursoRepository cursoRepository;
    UsuarioRepository usuarioRepository;
    CursoUsuarioRepository cursoUsuarioRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,  CursoRepository cursoRepository, CursoUsuarioRepository cursoUsuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository=cursoRepository;
        this.cursoUsuarioRepository=cursoUsuarioRepository;

    }


    public List<Usuario> getallUser() {
      return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Object> findByPhone(String phone) {
        return usuarioRepository.findByPhone(phone);
    }

    public void deleted(Integer id) {
        usuarioRepository.deleteById(Long.valueOf(id));
    }

    public CursoUsuario enrollInCourse(Long userId, Long courseId) {
        Usuario user=usuarioRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        Curso curso=cursoRepository.findById(courseId).orElseThrow(()-> new RuntimeException("Course not found"));
        CursoUsuario cursoUsuario=CursoUsuario.builder()
                .curso(curso)
                .usuario(user)
                .build();
        return cursoUsuarioRepository.save(cursoUsuario);
    }

    public void removeCurso(Long userId, Long cursoId) {
        Usuario user=usuarioRepository.findById(userId).orElseThrow( ()-> new RuntimeException("Usuario no encontrado"));
        Curso curso=cursoRepository.findById(cursoId).orElseThrow(()-> new RuntimeException("este id no exite "));
        CursoUsuario cursoUsuario=CursoUsuario.builder()
                .curso(curso)
                .usuario(user)
                .build();
         cursoUsuarioRepository.delete(cursoUsuario);
    }
}
