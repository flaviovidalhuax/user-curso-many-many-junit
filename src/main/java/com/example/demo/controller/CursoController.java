package com.example.demo.controller;

import com.example.demo.entity.Curso;
import com.example.demo.entity.CursoUsuario;
import com.example.demo.entity.Usuario;
import com.example.demo.servicio.CursoServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/curso")
public class CursoController {
    CursoServicio cursoServicio;
    @Autowired
    public CursoController(CursoServicio cursoServicio) {
        this.cursoServicio = cursoServicio;
    }
    @GetMapping("/all")
    public ResponseEntity<Object> allCurso(){
        List<Curso> listCurso=cursoServicio.allCurso();
        if(listCurso.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("lista vacia");
        }else {
           return ResponseEntity.status(HttpStatus.OK).body(listCurso);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id){
        Optional<Curso> cuso =cursoServicio.findById(id);
        if(cuso.isPresent()){
            return ResponseEntity.ok(cuso);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso con ID: "+id+" no exixte.");
        }
    }
    @PostMapping("/save")
    public ResponseEntity<Object> saveCruso(@Valid @RequestBody Curso curso, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> errores=new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errores.put(fieldError.getField(),"El campo "+fieldError.getField()+": "+ fieldError.getDefaultMessage()));
        return  ResponseEntity.badRequest().body(errores);
        }
        return ResponseEntity.ok().body(cursoServicio.saveCurso(curso));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleted(@PathVariable Integer id){
        Optional<Curso> curso=cursoServicio.findById(id);
        if(curso.isPresent()){
            cursoServicio.deletCurso(id);
            return ResponseEntity.status(HttpStatus.OK).body("Curso eliminado: "+curso.get().getNombre());
        }else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    @PostMapping("/{cursoId}/usuario/{usuarioId}")
    public ResponseEntity<CursoUsuario> agregarUsuarioACurso(@PathVariable Long cursoId, @PathVariable Long usuarioId){
          CursoUsuario relacion=cursoServicio.agregarUsuarioACurso(cursoId, usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(relacion);
    }


}
