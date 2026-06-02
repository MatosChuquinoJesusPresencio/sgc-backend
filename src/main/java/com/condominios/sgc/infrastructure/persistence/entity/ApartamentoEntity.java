package com.condominios.sgc.infrastructure.persistence.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PisoEntity piso;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", unique = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private UsuarioEntity propietario;
}
