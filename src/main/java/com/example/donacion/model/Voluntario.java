package com.example.donacion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class Voluntario {
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    private int edad;
    private String horario;
    private String turno;


    @ManyToOne
    @JsonIgnoreProperties("")
    private Usuario usuario;
}
