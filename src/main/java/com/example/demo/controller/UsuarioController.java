package com.example.demo.controller;

import com.example.demo.entity.CursoUsuario;
import com.example.demo.entity.Usuario;
import com.example.demo.servicio.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1/user")
public class UsuarioController {

    UsuarioService usuarioService;
    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAlluser(){
        List<Usuario> listUser=usuarioService.getallUser();
        if(listUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no hay usuarios en la lista.");
        }else {
            return ResponseEntity.ok(listUser);
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBiId(@PathVariable Long id){
        Optional<Usuario> user =usuarioService.findById(id);
        if(user.isPresent()){
           return ResponseEntity.status(HttpStatus.OK).body(user);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID "+id+" no exixte. 404");
        }

    }
    @PostMapping("/save")
    public ResponseEntity<Object> postUser(@Valid @RequestBody Usuario usuario, BindingResult result){
        if(usuarioService.findByEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "ya exixte el email!!"));
        }
        if(usuarioService.findByPhone(usuario.getPhone()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje2", "ya exixte el phone!!"));
        }
       if(result.hasErrors()){
           Map<String, String> error=new HashMap<>();
           result.getFieldErrors().forEach(fieldError -> error.put(fieldError.getField(), "El campo "+fieldError.getField()+" "+fieldError.getDefaultMessage()));
        return  ResponseEntity.badRequest().body(error);
       }
       Usuario usuario1=usuarioService.save(usuario);
       return ResponseEntity.status(HttpStatus.CREATED).body(usuario1);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> putUSer(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Integer id){
        if(result.hasErrors()){
            Map<String, String> error=new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> error.put(fieldError.getField(), "El campo "+fieldError.getField()+" "+fieldError.getDefaultMessage()));
            return  ResponseEntity.badRequest().body(error);
        }
        Optional<Usuario> user =usuarioService.findById(Long.valueOf(id));
        if (user.isPresent()){
            Usuario userDB =user.get();
            if (!usuario.getEmail().isEmpty() &&
                    usuario.getEmail().equalsIgnoreCase(userDB.getEmail()) &&
                    usuarioService.findByEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "ya existe usuario con este correo electronico"));
            }
            userDB.setFullname(usuario.getFullname());
            userDB.setAge(usuario.getAge());
            userDB.setEmail(usuario.getEmail());
            userDB.setPhone(usuario.getPhone());
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(userDB));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id){
       Optional<Usuario> usuario= usuarioService.findById(Long.valueOf(id));
       if(usuario.isPresent()){
           usuarioService.deleted(id);
           return ResponseEntity.status(HttpStatus.OK).body("Usuario eliminado con nombre: "+usuario.get().getFullname());
       }else {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }
    }

    @PostMapping("/{userId}/curso/{courseId}")
    public ResponseEntity<CursoUsuario> enrollinCourse(@PathVariable Long userId, @PathVariable Long courseId ){
        CursoUsuario user=usuarioService.enrollInCourse(userId, courseId);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/{userId}/curso/{cursoId}")
    public ResponseEntity<Void> removeCourse(@PathVariable Long userId, @PathVariable Long cursoId){
    usuarioService.removeCurso(userId,cursoId);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
