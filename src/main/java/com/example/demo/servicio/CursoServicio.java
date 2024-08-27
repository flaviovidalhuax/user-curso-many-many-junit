package com.example.demo.servicio;

import com.example.demo.entity.Curso;
import com.example.demo.entity.CursoUsuario;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.CursoUsuarioRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServicio {
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoUsuarioRepository cursoUsuarioRepository;
    public List<Curso> allCurso(){
        return cursoRepository.findAll();
    }
    public Optional<Curso> findById(Integer id){
        return cursoRepository.findById(Long.valueOf(id));
    }

    public Curso saveCurso(Curso curso){
        return cursoRepository.save(curso);
    }
    public void deletCurso(Integer id){
    Optional<Curso> curso =cursoRepository.findById(Long.valueOf(id));
    if(curso.isPresent()){
        cursoRepository.deleteById(id.longValue());
    }
    }

    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId){
        return Optional.empty();
    }

    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId){
        return Optional.empty();
    }

    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId){
        return Optional.empty();
    }

    public CursoUsuario agregarUsuarioACurso(Long cusroId, Long usuarioId) {
        Curso curso=cursoRepository.findById(cusroId).orElseThrow( ()-> new RuntimeException("este curso no existe con id:"+cusroId));
        Usuario usuario=usuarioRepository.findById(usuarioId).orElseThrow( ()-> new RuntimeException("Este usuario no existe con id: "+ usuarioId));
        CursoUsuario cursoUsuario=CursoUsuario.builder()
                .usuario(usuario)
                .curso(curso)
                .build();
        return cursoUsuarioRepository.save(cursoUsuario);
    }
}
