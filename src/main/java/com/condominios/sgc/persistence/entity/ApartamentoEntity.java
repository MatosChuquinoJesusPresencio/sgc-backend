package com.condominios.sgc.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "apartamento")
@Getter
@Setter
@NoArgsConstructor
public class ApartamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Boolean derechoEstacionamiento;

    @Column(nullable = false)
    private BigDecimal metraje;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "piso_id", nullable = false)
    private PisoEntity piso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id")
    private UsuarioEntity propietario;
}
