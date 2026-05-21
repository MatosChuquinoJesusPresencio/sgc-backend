package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "piso")
@Getter
@Setter
@NoArgsConstructor
public class PisoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "torre_id", nullable = false)
    private TorreEntity torre;

    @OneToMany(mappedBy = "piso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ApartamentoEntity> apartamentos = new ArrayList<>();
}
