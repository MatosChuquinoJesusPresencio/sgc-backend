package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.SecurityActiveCartLoanResult;
import com.condominios.sgc.application.dto.result.SecurityDashboardResult;
import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult;
import com.condominios.sgc.application.dto.result.SecurityVehicleVerificationResult;
import com.condominios.sgc.infrastructure.web.dto.SecurityActiveCartLoanResponse;
import com.condominios.sgc.infrastructure.web.dto.SecurityDashboardResponse;
import com.condominios.sgc.infrastructure.web.dto.SecurityDashboardResponse.SecurityRecentLogEntry;
import com.condominios.sgc.infrastructure.web.dto.SecurityParkingSlotResponse;
import com.condominios.sgc.infrastructure.web.dto.SecurityVehicleVerificationResponse;

@Component
public class SeguridadMapper {

    public SecurityDashboardResponse toDashboardResponse(SecurityDashboardResult r) {
        var entries = r.movimientosRecientes().stream()
            .map(e -> new SecurityRecentLogEntry(e.id(), e.tipo(), e.descripcion(), e.fecha()))
            .toList();
        return new SecurityDashboardResponse(
            r.totalEstacionamientos(), r.estacionamientosOcupados(),
            r.prestamosActivos(), entries);
    }

    public List<SecurityParkingSlotResponse> toParkingSlotResponses(List<SecurityParkingSlotResult> results) {
        return results.stream()
            .map(r -> new SecurityParkingSlotResponse(
                r.id(), r.numero(),
                r.tipoVehiculo() != null ? r.tipoVehiculo().name() : null,
                r.capacidadMaxima(), r.cantidadActual(), r.disponible()))
            .toList();
    }

    public SecurityVehicleVerificationResponse toVehicleVerificationResponse(SecurityVehicleVerificationResult r) {
        return new SecurityVehicleVerificationResponse(
            r.idVehiculo(), r.placa(), r.marca(), r.color(), r.modelo(),
            r.tipo().name(), r.idPropietario(), r.nombrePropietario(),
            r.idEstacionamiento());
    }

    public List<SecurityActiveCartLoanResponse> toActiveCartLoanResponses(List<SecurityActiveCartLoanResult> results) {
        return results.stream()
            .map(r -> new SecurityActiveCartLoanResponse(
                r.id(), r.nombreSolicitante(), r.dniSolicitante(),
                r.codigoCarrito(), r.fechaPrestamo(), r.penalizacion()))
            .toList();
    }

    public SecurityActiveCartLoanResponse toActiveCartLoanResponse(SecurityActiveCartLoanResult r) {
        return new SecurityActiveCartLoanResponse(
            r.id(), r.nombreSolicitante(), r.dniSolicitante(),
            r.codigoCarrito(), r.fechaPrestamo(), r.penalizacion());
    }
}
