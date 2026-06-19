package com.condominios.sgc.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.TorreException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class CondominioModel {
    private Long id;
    private String nombre;
    private Long idPais;
    private Long idCiudad;
    private String direccion;
    private Boolean activo;
    private Instant fechaCreacion;
    private List<TorreModel> torres;
    private ConfiguracionModel configuracion;

    public CondominioModel(Long id, String nombre, Long idPais,
            Long idCiudad, String direccion, Boolean activo, Instant fechaCreacion,
            List<TorreModel> torres, ConfiguracionModel configuracion) {
        this.id = id;
        this.nombre = nombre;
        this.idPais = idPais;
        this.idCiudad = idCiudad;
        this.direccion = direccion;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        this.torres = new ArrayList<>(torres);
        this.configuracion = configuracion;
    }

    public CondominioModel(String nombre, Long idPais, Long idCiudad, String direccion) {
        this.id = null;
        this.nombre = requerido(nombre, CondominioException::nombreRequerido);
        this.idPais = noNulo(idPais, CondominioException::paisRequerido);
        this.idCiudad = noNulo(idCiudad, CondominioException::ciudadRequerido);
        this.direccion = requerido(direccion, CondominioException::direccionRequerida);
        this.activo = false;
        this.fechaCreacion = Instant.now();
        this.torres = new ArrayList<>();
        this.configuracion = ConfiguracionModel.nuevo();
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Long getIdPais() { return idPais; }
    public Long getIdCiudad() { return idCiudad; }
    public String getDireccion() { return direccion; }
    public Boolean getActivo() { return activo; }
    public Instant getFechaCreacion() { return fechaCreacion; }

    public List<TorreModel> getTorres() {
        return Collections.unmodifiableList(torres);
    }

    public ConfiguracionModel getConfiguracion() {
        return configuracion;
    }

    public void actualizar(String nombre, Long idPais, Long idCiudad, String direccion) {
        this.nombre = requerido(nombre, CondominioException::nombreRequerido);
        this.idPais = noNulo(idPais, CondominioException::paisRequerido);
        this.idCiudad = noNulo(idCiudad, CondominioException::ciudadRequerido);
        this.direccion = requerido(direccion, CondominioException::direccionRequerida);
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public TorreModel agregarTorre(String nombre) {
        var torre = new TorreModel(nombre);
        torres.add(torre);
        return torre;
    }

    public PisoModel agregarPiso(String nombreTorre, Integer numero) {
        var torre = buscarTorre(nombreTorre);
        return torre.agregarPiso(numero);
    }

    public ApartamentoModel agregarApartamento(String nombreTorre, Integer numeroPiso,
            Integer numeroApartamento, BigDecimal metraje) {
        var torre = buscarTorre(nombreTorre);
        var piso = torre.buscarPisoPorNumero(numeroPiso);
        return piso.agregarApartamento(numeroApartamento, metraje);
    }

    private TorreModel buscarTorre(String nombre) {
        return torres.stream()
            .filter(t -> t.getNombre().equals(nombre))
            .findFirst()
            .orElseThrow(TorreException::noEncontrado);
    }
}
