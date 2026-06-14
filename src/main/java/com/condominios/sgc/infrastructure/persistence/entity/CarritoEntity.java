package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carritos")
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCarrito estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private CondominioEntity condominio;

    @Column(name = "id_condominio", insertable = false, updatable = false)
    private Long idCondominio;

    @OneToMany(mappedBy = "carrito")
    private List<LogPrestamoCarritoEntity> logsPrestamoCarrito = new ArrayList<>();

    public CarritoEntity() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public EstadoCarrito getEstado() { return estado; }
    public void setEstado(EstadoCarrito estado) { this.estado = estado; }
    public CondominioEntity getCondominio() { return condominio; }
    public void setCondominio(CondominioEntity condominio) { this.condominio = condominio; }
    public Long getIdCondominio() { return idCondominio; }
    public void setIdCondominio(Long idCondominio) { this.idCondominio = idCondominio; }
}
