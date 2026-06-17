package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "apartamentos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApartamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(name = "derecho_estacionamiento")
    private Boolean derechoEstacionamiento;

    private BigDecimal metraje;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario", unique = true)
    private UsuarioEntity propietario;

    @Column(name = "id_propietario", insertable = false, updatable = false)
    private Long idPropietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_piso")
    private PisoEntity piso;

    @Column(name = "id_piso", insertable = false, updatable = false)
    private Long idPiso;

    @OneToMany(mappedBy = "apartamento")
    private List<InquilinoEntity> inquilinos = new ArrayList<>();

    @OneToMany(mappedBy = "apartamento")
    private List<EstacionamientoEntity> estacionamientos = new ArrayList<>();

    @OneToMany(mappedBy = "apartamento")
    private List<LogPrestamoCarritoEntity> logsPrestamoCarrito = new ArrayList<>();
}
