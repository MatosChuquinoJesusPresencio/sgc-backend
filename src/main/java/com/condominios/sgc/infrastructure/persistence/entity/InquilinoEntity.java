package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inquilinos")
@NoArgsConstructor
@AllArgsConstructor
public class InquilinoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apartamento")
    private ApartamentoEntity apartamento;

    @Column(name = "id_apartamento", insertable = false, updatable = false)
    private Long idApartamento;

    @OneToOne(mappedBy = "inquilino")
    private VehiculoEntity vehiculo;

    @OneToMany(mappedBy = "inquilino")
    private List<LogPrestamoCarritoEntity> logsPrestamoCarrito = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumento tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public ApartamentoEntity getApartamento() { return apartamento; }
    public void setApartamento(ApartamentoEntity apartamento) { this.apartamento = apartamento; }
    public Long getIdApartamento() { return idApartamento; }
    public void setIdApartamento(Long idApartamento) { this.idApartamento = idApartamento; }
    public VehiculoEntity getVehiculo() { return vehiculo; }
    public void setVehiculo(VehiculoEntity vehiculo) { this.vehiculo = vehiculo; }
}
