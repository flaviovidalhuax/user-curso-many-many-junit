package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Seguro")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="debe tener un nombre")
    private String nombreSeguro;
    @Positive(message = "El costo debe ser un valor positivo")
    @Min(value = 0, message = "El costo debe ser al menos 0")
    private Integer costo;

    @OneToMany(mappedBy = "seguro", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"seguro", "listseguros", "usuarioCursos"})
    private Set<SeguroUsuario> listseguros = new HashSet<>();


}
