package com.example.demo.response;

import com.example.demo.entity.SeguroUsuario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class ResponceFile {
    private String name;
    private String url;
    private String type;
    private long size;

   }
