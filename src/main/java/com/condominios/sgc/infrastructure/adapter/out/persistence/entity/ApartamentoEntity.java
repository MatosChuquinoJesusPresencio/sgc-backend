package com.condominios.sgc.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piso_id", nullable = false)
    private PisoEntity piso;

    @Column(name = "propietario_id")
    private Long idPropietario;
}
