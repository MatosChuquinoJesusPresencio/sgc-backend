package com.condominios.sgc.domain.model;

import java.time.Instant;

import com.condominios.sgc.domain.exception.CondominioException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class CondominioModel {
    private Long id;
    private String nombre;
    private Long idPais;
    private Long idCiudad;
    private String direccion;
    private Instant fechaCreacion;

    public CondominioModel(Long id, String nombre, Long idPais, 
            Long idCiudad, String direccion, Instant fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.idPais = idPais;
        this.idCiudad = idCiudad;
        this.direccion = direccion;
        this.fechaCreacion = fechaCreacion;
    }

    public CondominioModel(String nombre, Long idPais, Long idCiudad, String direccion) {
        this.id = null;
        this.nombre = requerido(nombre, CondominioException::nombreRequerido);
        this.idPais = noNulo(idPais, CondominioException::paisRequerido);
        this.idCiudad = noNulo(idCiudad, CondominioException::ciudadRequerido);
        this.direccion = direccion;
        this.fechaCreacion = Instant.now();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getIdPais() { return idPais; }
    public Long getIdCiudad() { return idCiudad; }
    public String getDireccion() { return direccion; }
    public Instant getFechaCreacion() { return fechaCreacion; }

    public void actualizar(String nombre, Long idPais, Long idCiudad, String direccion) {
        this.nombre = requerido(nombre, CondominioException::nombreRequerido);
        this.idPais = noNulo(idPais, CondominioException::paisRequerido);
        this.idCiudad = noNulo(idCiudad, CondominioException::ciudadRequerido);
        this.direccion = direccion;
    }
}
