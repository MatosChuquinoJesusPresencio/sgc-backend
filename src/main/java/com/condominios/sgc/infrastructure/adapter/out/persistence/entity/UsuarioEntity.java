package com.condominios.sgc.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;

    @Column(nullable = false)
    private String rol;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private String contrasena;

    private String correoPendiente;

    @Column(nullable = false)
    private Boolean correoVerificado;

    @Column(name = "condominio_id")
    private Long idCondominio;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
}
