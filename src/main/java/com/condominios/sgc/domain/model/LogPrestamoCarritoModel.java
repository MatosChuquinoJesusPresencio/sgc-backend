package com.condominios.sgc.domain.model;


import java.math.BigDecimal;
import java.time.Instant;

import com.condominios.sgc.domain.type.TipoHabitante;
import com.condominios.sgc.domain.shared.exception.LogPrestamoCarritoException;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class LogPrestamoCarritoModel {
    private Long id;
    private TipoHabitante solicitante;
    private String nombreSolicitante;
    private String dniSolicitante;
    private BigDecimal penalizacion;
    private Instant fechaPrestamo;
    private Instant fechaDevolucion;
    private Long idApartamento;
    private Long idCarrito;
    private Long idInquilino;
    private Long idPropietario;
    private Long idCondominio;

    public LogPrestamoCarritoModel(Long id, TipoHabitante solicitante, 
            String nombreSolicitante, String dniSolicitante, BigDecimal penalizacion, 
            Instant fechaPrestamo, Instant fechaDevolucion, Long idApartamento, 
            Long idCarrito, Long idInquilino, Long idPropietario, Long idCondominio) {
        this.id = id;
        this.solicitante = solicitante;
        this.nombreSolicitante = nombreSolicitante;
        this.dniSolicitante = dniSolicitante;
        this.penalizacion = penalizacion;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.idApartamento = idApartamento;
        this.idCarrito = idCarrito;
        this.idInquilino = idInquilino;
        this.idPropietario = idPropietario;
        this.idCondominio = idCondominio;
    }

    public LogPrestamoCarritoModel(String nombreSolicitante, String dniSolicitante, 
        Long idApartamento, Long idCarrito, Long idPropietario, Long idInquilino, Long idCondominio) {
        this.id = null;
        this.nombreSolicitante = requerido(nombreSolicitante, LogPrestamoCarritoException::nombreSolicitanteRequerido);
        this.dniSolicitante = requerido(dniSolicitante, LogPrestamoCarritoException::dniSolicitanteRequerido);
        this.penalizacion = BigDecimal.ZERO;
        this.fechaPrestamo = Instant.now();
        this.fechaDevolucion = null;
        this.idApartamento = noNulo(idApartamento, LogPrestamoCarritoException::apartamentoRequerido);
        this.idCarrito = noNulo(idCarrito, LogPrestamoCarritoException::carritoRequerido);
        this.idCondominio = noNulo(idCondominio, LogPrestamoCarritoException::condominioRequerido);
        asignarSolicitante(idPropietario, idInquilino);
    }

    public Long getId() { return id; }
    public TipoHabitante getSolicitante() { return solicitante; }
    public String getNombreSolicitante() { return nombreSolicitante; }
    public String getDniSolicitante() { return dniSolicitante; }
    public BigDecimal getPenalizacion() { return penalizacion; }
    public Instant getFechaPrestamo() { return fechaPrestamo; }
    public Instant getFechaDevolucion() { return fechaDevolucion; }
    public Long getIdApartamento() { return idApartamento; }
    public Long getIdCarrito() { return idCarrito; }
    public Long getIdInquilino() { return idInquilino; }
    public Long getIdPropietario() { return idPropietario; }
    public Long getIdCondominio() { return idCondominio; }

    private void asignarPropietario(Long idPropietario) {
        this.idPropietario = noNulo(idPropietario, LogPrestamoCarritoException::propietarioRequerido);
        this.idInquilino = null;
        this.solicitante = TipoHabitante.PROPIETARIO;
    }

    private void asignarInquilino(Long idInquilino) {
        this.idInquilino = noNulo(idInquilino, LogPrestamoCarritoException::inquilinoRequerido);
        this.idPropietario = null;
        this.solicitante = TipoHabitante.INQUILINO;
    }

    private void asignarSolicitante(Long idPropietario, Long idInquilino) {
        boolean hayPropietario = idPropietario != null;
        boolean hayInquilino = idInquilino != null;
        if (hayInquilino == hayPropietario)
            throw LogPrestamoCarritoException.solicitanteInvalido();

        if (hayInquilino) {
            asignarInquilino(idInquilino);
        } else {
            asignarPropietario(idPropietario);
        }
    }

    public void registrarDevolucion(BigDecimal penalizacion) {
        if (this.fechaDevolucion != null)
            throw LogPrestamoCarritoException.devolucionYaRegistrada();
        this.penalizacion = noNulo(penalizacion, LogPrestamoCarritoException::penalizacionRequerida);
        this.fechaDevolucion = Instant.now();
    }
}
