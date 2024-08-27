package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Seguro_usuario")
public class SeguroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnoreProperties({ "listseguros" })
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "seguro_id", nullable = false)
    @JsonIgnoreProperties({ "listseguros" })
    private Seguro seguro;

}
