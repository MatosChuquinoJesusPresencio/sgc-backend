package com.condominios.sgc.infrastructure.persistence.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import com.condominios.sgc.domain.auxiliar.TipoVehiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehiculo")
@Getter
@Setter
@NoArgsConstructor
public class VehiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true)
    private String placa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVehiculo tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsuarioEntity propietario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquilino_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private InquilinoEntity inquilino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estacionamiento_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private EstacionamientoEntity estacionamiento;
}
