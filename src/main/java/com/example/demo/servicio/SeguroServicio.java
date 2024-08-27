package com.example.demo.servicio;

import com.example.demo.entity.Seguro;
import com.example.demo.entity.SeguroUsuario;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.SeguroRepository;
import com.example.demo.repository.SeguroUsuarioRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeguroServicio {

    @Autowired
    SeguroRepository seguroRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SeguroUsuarioRepository seguroUsuarioRepository;
    public List<Seguro> findAllSeguros() {
        return seguroRepository.findAll();
    }

    public Seguro save(Seguro seguro) {
        return seguroRepository.save(seguro);
    }

    public SeguroUsuario inscribirUsuarioAseguro(Long seguroId, Long userId){
        Seguro seguro=seguroRepository.findById(seguroId).orElseThrow(()-> new RuntimeException("seguro no encontrado"));
        Usuario usuario=usuarioRepository.findById(userId).orElseThrow(()-> new RuntimeException("seguro no encontrado"));

        SeguroUsuario seguroUsuario=SeguroUsuario.builder()
                        .seguro(seguro)
                                .usuario(usuario)
                                        .build();
       return seguroUsuarioRepository.save(seguroUsuario);
    }
}
