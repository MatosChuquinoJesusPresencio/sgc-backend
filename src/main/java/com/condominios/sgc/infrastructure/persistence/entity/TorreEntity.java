package com.condominios.sgc.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "torre")
@Getter
@Setter
@NoArgsConstructor
public class TorreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "condominio_id", nullable = false)
    private CondominioEntity condominio;

    @OneToMany(mappedBy = "torre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PisoEntity> pisos = new ArrayList<>();
}
