package com.condominios.sgc.web.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record DetalleApartamentoResponse(
    Long id,
    Integer numero,
    BigDecimal metraje,
    Boolean derechoEstacionamiento,
    PropietarioResumen propietario,
    Integer piso,
    String torre,
    String condominio,
    List<InquilinoResumen> inquilinos,
    List<EstacionamientoResumen> estacionamientos
) {
    public static DetalleApartamentoResponse desdeAplicacion(com.condominios.sgc.application.dto.response.DetalleApartamentoResponse app) {
        return new DetalleApartamentoResponse(
                app.id(), app.numero(), app.metraje(), app.derechoEstacionamiento(),
                app.propietario() != null
                        ? new PropietarioResumen(app.propietario().id(), app.propietario().nombres(),
                                app.propietario().apellidos(), app.propietario().correo(), app.propietario().telefono())
                        : null,
                app.piso(), app.torre(), app.condominio(),
                app.inquilinos().stream()
                        .map(i -> new InquilinoResumen(i.id(), i.nombres(), i.apellidos(),
                                i.tipoDocumento(), i.numeroDocumento()))
                        .toList(),
                app.estacionamientos().stream()
                        .map(e -> new EstacionamientoResumen(e.id(), e.numero(),
                                e.tipoVehiculo(), e.disponible()))
                        .toList());
    }

    public record PropietarioResumen(Long id, String nombres, String apellidos, String correo, String telefono) {}

    public record InquilinoResumen(Long id, String nombres, String apellidos, String tipoDocumento, String numeroDocumento) {}

    public record EstacionamientoResumen(Long id, Integer numero, String tipoVehiculo, Boolean disponible) {}
}
