package com.example.donacion.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table
@Entity
public class Solicitud_Voluntario {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Solicitud solicitud;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Voluntario voluntario;

    private Date fecha;
}
