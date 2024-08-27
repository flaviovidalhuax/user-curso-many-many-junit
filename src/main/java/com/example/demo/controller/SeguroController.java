package com.example.demo.controller;

import com.example.demo.entity.Curso;
import com.example.demo.entity.Seguro;
import com.example.demo.entity.SeguroUsuario;
import com.example.demo.servicio.SeguroServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/seguro/")
public class SeguroController {

    @Autowired
    SeguroServicio seguroServicio;
    @GetMapping("/all")
    public ResponseEntity<List<Seguro>> getalUser(){
        List<Seguro> allSeguros=seguroServicio.findAllSeguros();
        return ResponseEntity.ok(allSeguros);
    }
    @PostMapping("/save")
    public ResponseEntity<Object> postSeguro(@Valid @RequestBody Seguro seguro, BindingResult result){
        if(result.hasErrors()){
            Map<String, String> error=new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> error.put(fieldError.getField(), "El campo "+fieldError.getField()+" "+fieldError.getDefaultMessage()));
            return  ResponseEntity.badRequest().body(error);
        }
         Seguro seguroDb=seguroServicio.save(seguro);
        return ResponseEntity.status(HttpStatus.CREATED).body(seguroDb);
    }
    @PostMapping("/{seguroId}/inscribir/{userId}")
    public ResponseEntity<Object> asignarSeguroUsuario(@PathVariable Long seguroId, @PathVariable Long userId){
        SeguroUsuario inscrip=seguroServicio.inscribirUsuarioAseguro(seguroId, userId);
        return new ResponseEntity<>(inscrip, HttpStatus.CREATED);
    }





}
