package com.condominios.sgc.infrastructure.persistence.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "estacionamiento")
@Getter
@Setter
@NoArgsConstructor
public class EstacionamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipoVehiculo;

    private Integer capacidadMaxima;

    @Column(nullable = false)
    private Integer cantidadActual = 0;

    @Column(nullable = false)
    private Boolean disponible = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condominio_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CondominioEntity condominio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartamento_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private ApartamentoEntity apartamento;
}
