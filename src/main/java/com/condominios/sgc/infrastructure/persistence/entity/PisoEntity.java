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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pisos")
@NoArgsConstructor
@AllArgsConstructor
public class PisoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_torre")
    private TorreEntity torre;

    @Column(name = "id_torre", insertable = false, updatable = false)
    private Long idTorre;

    @OneToMany(mappedBy = "piso")
    private List<ApartamentoEntity> apartamentos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public TorreEntity getTorre() { return torre; }
    public void setTorre(TorreEntity torre) { this.torre = torre; }
    public Long getIdTorre() { return idTorre; }
    public void setIdTorre(Long idTorre) { this.idTorre = idTorre; }
    public List<ApartamentoEntity> getApartamentos() { return apartamentos; }
    public void setApartamentos(List<ApartamentoEntity> apartamentos) { this.apartamentos = apartamentos; }
}
