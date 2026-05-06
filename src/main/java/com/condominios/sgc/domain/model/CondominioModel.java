package com.condominios.sgc.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.condominios.sgc.domain.exception.CarritoException;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.exception.EstacionamientoException;
import com.condominios.sgc.domain.exception.TorreException;

public class CondominioModel {
    private Long id;
    private String nombre;
    private String pais;
    private String ciudad;
    private String direccion;
    private LocalDateTime fechaCreacion;
    private Long configuracionId;
    private List<Long> torreIds;
    private List<Long> estacionamientoIds;
    private List<Long> carritoIds;

    public CondominioModel(Long id, String nombre, String pais, String ciudad, String direccion,
            LocalDateTime fechaCreacion, Long configuracionId, List<Long> torreIds,
            List<Long> estacionamientoIds, List<Long> carritoIds) {
        this.id = id;
        validarYAsignarDatos(nombre, pais, ciudad, direccion, fechaCreacion);
        validarYAsignarConfiguracion(configuracionId);
        this.torreIds = torreIds != null ? new ArrayList<>(torreIds) : new ArrayList<>();
        this.estacionamientoIds = estacionamientoIds != null ? new ArrayList<>(estacionamientoIds) : new ArrayList<>();
        this.carritoIds = carritoIds != null ? new ArrayList<>(carritoIds) : new ArrayList<>();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPais() { return pais; }
    public String getCiudad() { return ciudad; }
    public String getDireccion() { return direccion; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public Long getConfiguracionId() { return configuracionId; }
    public List<Long> getTorreIds() { return torreIds; }
    public List<Long> getEstacionamientoIds() { return estacionamientoIds; }
    public List<Long> getCarritoIds() { return carritoIds; }

    private void validarYAsignarDatos(String nombre, String pais, String ciudad, String direccion,
            LocalDateTime fechaCreacion) {
        
        if (nombre == null || nombre.trim().isEmpty()) {
            throw CondominioException.nombreObligatorio();
        }
        this.nombre = nombre;

        if (pais == null || pais.trim().isEmpty()) {
            throw CondominioException.paisObligatorio();
        }
        this.pais = pais;

        if (ciudad == null || ciudad.trim().isEmpty()) {
            throw CondominioException.ciudadObligatoria();
        }
        this.ciudad = ciudad;

        if (direccion == null || direccion.trim().isEmpty()) {
            throw CondominioException.direccionObligatoria();
        }
        this.direccion = direccion;

        if (fechaCreacion == null) {
            throw CondominioException.fechaCreacionObligatoria();
        }
        this.fechaCreacion = fechaCreacion;
    }

    private void validarYAsignarConfiguracion(Long configuracionId) {
        if (configuracionId == null) {
            throw CondominioException.sinConfiguracion();
        }
        this.configuracionId = configuracionId;
    }

    public void actualizarInformacion(String nombre, String pais, String ciudad, String direccion) {
        validarYAsignarDatos(nombre, pais, ciudad, direccion, this.fechaCreacion);
    }

    public void actualizarConfiguracion(Long configuracionId) {
        validarYAsignarConfiguracion(configuracionId);
    }

    public void agregarTorre(Long torreId) {
        if (torreId == null) {
            throw CondominioException.torreIdObligatorio();
        }
        if (contieneTorrePorId(torreId)) {
            throw TorreException.torreYaExistePorId(torreId);
        }
        torreIds.add(torreId);
    }

    public void agregarEstacionamiento(Long estacionamientoId) {
        if (estacionamientoId == null) {
            throw CondominioException.estacionamientoIdObligatorio();
        }
        if (contieneEstacionamientoPorId(estacionamientoId)) {
            throw EstacionamientoException.estacionamientoYaExistePorId(estacionamientoId);
        }
        estacionamientoIds.add(estacionamientoId);
    }

    public void agregarCarrito(Long carritoId) {
        if (carritoId == null) {
            throw CondominioException.carritoIdObligatorio();
        }
        if (contieneCarritoPorId(carritoId)) {
            throw CarritoException.carritoYaExistePorId(carritoId);
        }
        carritoIds.add(carritoId);
    }

    public boolean contieneTorrePorId(Long torreId) {
        return torreIds.stream().anyMatch(t -> Objects.equals(t, torreId));
    }

    public boolean contieneEstacionamientoPorId(Long estacionamientoId) {
        return estacionamientoIds.stream().anyMatch(e -> Objects.equals(e, estacionamientoId));
    }

    public boolean contieneCarritoPorId(Long carritoId) {
        return carritoIds.stream().anyMatch(c -> Objects.equals(c, carritoId));
    }

    public void eliminarTorrePorId(Long torreId) {
        if (torreId == null) {
            throw CondominioException.torreIdObligatorio();
        }
        if (!contieneTorrePorId(torreId)) {
            throw TorreException.torreNoExistePorId(torreId);
        }
        torreIds.removeIf(t -> Objects.equals(t, torreId));
    }

    public void eliminarEstacionamientoPorId(Long estacionamientoId) {
        if (estacionamientoId == null) {
            throw CondominioException.estacionamientoIdObligatorio();
        }
        if (!contieneEstacionamientoPorId(estacionamientoId)) {
            throw EstacionamientoException.estacionamientoNoExistePorId(estacionamientoId);
        }
        estacionamientoIds.removeIf(e -> Objects.equals(e, estacionamientoId));
    }

    public void eliminarCarritoPorId(Long carritoId) {
        if (carritoId == null) {
            throw CondominioException.carritoIdObligatorio();
        }
        if (!contieneCarritoPorId(carritoId)) {
            throw CarritoException.carritoNoExistePorId(carritoId);
        }
        carritoIds.removeIf(c -> Objects.equals(c, carritoId));
    }
}
