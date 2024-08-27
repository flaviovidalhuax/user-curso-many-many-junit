package com.example.demo.servicio;

import com.example.demo.entity.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;
    private Usuario usuario;
    @BeforeEach
    void setup(){
        usuario=Usuario.builder().fullname("flavio hernandez").age(23).email("flavio@gmail.com").phone("1234123489").id(1L).build();
    }

    @DisplayName("all user")
    @Test
    void getallUser() {
        given(usuarioRepository.findAll()).willReturn(List.of(usuario));
        List<Usuario> usuAll=usuarioService.getallUser();
        assertNotNull(usuAll);
        assertEquals(usuAll, List.of(usuario));

    }


    @Test
    void findById() {
        given(usuarioRepository.findById(usuario.getId())).willReturn(Optional.ofNullable(usuario));
        Optional<Usuario> usr=usuarioService.findById(usuario.getId());
        assertNotNull(usr);
        assertEquals(usr.get(), usuario);

    }

    @Test
    void findByEmail() {
        given(usuarioRepository.findByEmail(usuario.getEmail())).willReturn(Optional.ofNullable(usuario));
        Optional<Usuario> usr=usuarioService.findByEmail(usuario.getEmail());
        assertNotNull(usr);
    }

    @Test
    void save() {
    }

    @Test
    void findByPhone() {
    }

    @Test
    void deleted() {
    }
}