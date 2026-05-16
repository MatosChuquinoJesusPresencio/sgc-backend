package com.condominios.sgc.domain.model;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;

public class LogPrestamoCarritoModel {
    private Long id;
    private TipoHabitante solicitante;
    private BigDecimal penalizacion;
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucion;
    private Long apartamentoId;
    private Long carritoId;
    private Long inquilinoId;
    private String usuarioId;

    public LogPrestamoCarritoModel(TipoHabitante solicitante, Long apartamentoId, Long carritoId) {
        validarYAsignarDatos(solicitante, apartamentoId, carritoId);
        this.solicitante = solicitante;
        this.apartamentoId = apartamentoId;
        this.carritoId = carritoId;
        this.fechaPrestamo = LocalDateTime.now();
        this.penalizacion = BigDecimal.ZERO;
    }

    public LogPrestamoCarritoModel(
            Long id,
            TipoHabitante solicitante,
            Long apartamentoId,
            Long carritoId,
            String usuarioId,
            Long inquilinoId,
            BigDecimal penalizacion,
            LocalDateTime fechaPrestamo,
            LocalDateTime fechaDevolucion) {
        validarYAsignarDatos(solicitante, apartamentoId, carritoId);
        this.id = id;
        this.usuarioId = usuarioId;
        this.inquilinoId = inquilinoId;
        this.penalizacion = penalizacion;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    private void validarYAsignarDatos(TipoHabitante solicitante, Long apartamentoId, Long carritoId) {
        this.solicitante = requerirNoNulo(solicitante, LogPrestamoCarritoException::solicitanteObligatorio);
        this.apartamentoId = requerirNoNulo(apartamentoId, LogPrestamoCarritoException::apartamentoIdObligatorio);
        this.carritoId = requerirNoNulo(carritoId, LogPrestamoCarritoException::carritoObligatorio);
    }

    public Long getId() { return id; }
    public TipoHabitante getSolicitante() { return solicitante; }
    public BigDecimal getPenalizacion() { return penalizacion; }
    public LocalDateTime getFechaPrestamo() { return fechaPrestamo; }
    public LocalDateTime getFechaDevolucion() { return fechaDevolucion; }
    public Long getApartamentoId() { return apartamentoId; }
    public Long getCarritoId() { return carritoId; }
    public Long getInquilinoId() { return inquilinoId; }
    public String getUsuarioId() { return usuarioId; }

    public void asignarUsuarioSolicitante(String usuarioId) {
        this.usuarioId = requerirNoNulo(usuarioId, LogPrestamoCarritoException::usuarioIdObligatorio);
        requerirQue(this.inquilinoId == null, LogPrestamoCarritoException::yaTieneSolicitanteInquilino);
    }

    public void asignarInquilinoSolicitante(Long inquilinoId) {
        this.inquilinoId = requerirNoNulo(inquilinoId, LogPrestamoCarritoException::inquilinoIdObligatorio);
        requerirQue(this.usuarioId == null, LogPrestamoCarritoException::yaTieneSolicitanteUsuario);
    }

    public void registrarDevolucion(BigDecimal penalizacionAplicada) {
        requerirQue(this.fechaDevolucion == null, LogPrestamoCarritoException::devolucionYaRegistrada);
        this.fechaDevolucion = LocalDateTime.now();
        this.penalizacion = penalizacionAplicada != null ? penalizacionAplicada : BigDecimal.ZERO;
    }
}
