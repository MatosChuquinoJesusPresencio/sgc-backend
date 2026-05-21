package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id", unique = true)
    private UsuarioEntity propietario;

    @OneToMany(mappedBy = "apartamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InquilinoEntity> inquilinos = new ArrayList<>();

    @OneToMany(mappedBy = "apartamento")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<LogPrestamoCarritoEntity> logsPrestamoCarrito = new ArrayList<>();

    @OneToMany(mappedBy = "apartamento")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<EstacionamientoEntity> estacionamientos = new ArrayList<>();
}
