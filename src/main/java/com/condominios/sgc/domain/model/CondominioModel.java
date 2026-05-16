package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import java.time.LocalDateTime;

import com.condominios.sgc.domain.exception.CondominioException;

public class CondominioModel {
    private Long id;
    private String nombre;
    private String pais;
    private String ciudad;
    private String direccion;
    private LocalDateTime fechaCreacion;

    public CondominioModel(
        Long id,
        String nombre,
        String pais,
        String ciudad,
        String direccion,
        LocalDateTime fechaCreacion
    ) {
        this(nombre, pais, ciudad, direccion, fechaCreacion);
        this.id = id;
    }

    public CondominioModel(
        String nombre,
        String pais,
        String ciudad,
        String direccion,
        LocalDateTime fechaCreacion
    ) {
        validarYAsignarDatos(nombre, pais, ciudad, direccion, fechaCreacion);
    }

    private void validarYAsignarDatos(String nombre, String pais, String ciudad, String direccion, LocalDateTime fechaCreacion) {
        this.nombre = requerirNoVacio(nombre, CondominioException::nombreObligatorio);
        this.pais = requerirNoVacio(pais, CondominioException::paisObligatorio);
        this.ciudad = requerirNoVacio(ciudad, CondominioException::ciudadObligatoria);
        this.direccion = requerirNoVacio(direccion, CondominioException::direccionObligatoria);
        this.fechaCreacion = requerirNoNulo(fechaCreacion, CondominioException::fechaCreacionObligatoria);
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public String getDireccion() { return direccion; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public void actualizarDatos(
        String nombre,
        String pais,
        String ciudad,
        String direccion
    ) {
        validarYAsignarDatos(nombre, pais, ciudad, direccion, this.fechaCreacion);
    }
}
