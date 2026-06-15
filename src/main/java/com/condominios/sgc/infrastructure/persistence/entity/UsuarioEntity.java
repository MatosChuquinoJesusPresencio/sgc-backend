package com.condominios.sgc.infrastructure.persistence.entity;

import com.condominios.sgc.domain.auxiliar.Rol;
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
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(nullable = false)
    private Boolean activo;

    @Column(nullable = false)
    private String contrasena;

    @Column(name = "correo_pendiente")
    private String correoPendiente;

    @Column(name = "correo_verificado")
    private Boolean correoVerificado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_condominio")
    private CondominioEntity condominio;

    @Column(name = "id_condominio", insertable = false, updatable = false)
    private Long idCondominio;

    @OneToOne(mappedBy = "propietario")
    private ApartamentoEntity apartamento;

    

    @OneToMany(mappedBy = "propietario")
    private List<VehiculoEntity> vehiculosPropietario = new ArrayList<>();

    @OneToMany(mappedBy = "propietario")
    private List<LogPrestamoCarritoEntity> logsPrestamoCarrito = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<TokenEntity> tokens = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public String getCorreoPendiente() { return correoPendiente; }
    public void setCorreoPendiente(String correoPendiente) { this.correoPendiente = correoPendiente; }
    public Boolean getCorreoVerificado() { return correoVerificado; }
    public void setCorreoVerificado(Boolean correoVerificado) { this.correoVerificado = correoVerificado; }
    public CondominioEntity getCondominio() { return condominio; }
    public void setCondominio(CondominioEntity condominio) { this.condominio = condominio; }
    public Long getIdCondominio() { return idCondominio; }
    public void setIdCondominio(Long idCondominio) { this.idCondominio = idCondominio; }
    public ApartamentoEntity getApartamento() { return apartamento; }
    public void setApartamento(ApartamentoEntity apartamento) { this.apartamento = apartamento; }
    public List<VehiculoEntity> getVehiculosPropietario() { return vehiculosPropietario; }
    public List<LogPrestamoCarritoEntity> getLogsPrestamoCarrito() { return logsPrestamoCarrito; }
    public List<TokenEntity> getTokens() { return tokens; }
}
