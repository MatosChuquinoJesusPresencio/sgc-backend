package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private Boolean activo;

    private String passwordHash;

    private String correoPendiente;

    @Column(nullable = false)
    private Boolean correoVerificado = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condominio_id")
    private CondominioEntity condominio;
}
