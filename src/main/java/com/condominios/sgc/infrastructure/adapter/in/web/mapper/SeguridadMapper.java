package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.SecurityActiveCartLoanResult;
import com.condominios.sgc.application.dto.result.SecurityCartLoanFullResult;
import com.condominios.sgc.application.dto.result.SecurityDashboardResult;
import com.condominios.sgc.application.dto.result.SecurityParkingSlotResult;
import com.condominios.sgc.application.dto.result.SecurityUnassignedVehicleResult;
import com.condominios.sgc.application.dto.result.SecurityVehicleVerificationResult;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityActiveCartLoanResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityApartamentoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityCartAssetResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityCartLoanFullResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityDashboardResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityDashboardResponse.SecurityRecentLogEntry;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityParkingSlotResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityUnassignedVehicleResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.SecurityVehicleVerificationResponse;

@Component
public class SeguridadMapper {

    public SecurityDashboardResponse toDashboardResponse(SecurityDashboardResult r) {
        var entries = r.movimientosRecientes().stream()
            .map(e -> new SecurityRecentLogEntry(e.id(), e.tipo(), e.descripcion(), fmt(e.fecha())))
            .toList();
        return new SecurityDashboardResponse(
            r.totalEstacionamientos(), r.estacionamientosOcupados(),
            r.prestamosActivos(), entries);
    }

    public List<SecurityParkingSlotResponse> toParkingSlotResponses(List<SecurityParkingSlotResult> results) {
        return results.stream()
            .map(r -> {
                var vehiculos = r.vehiculos() != null
                    ? r.vehiculos().stream()
                        .map(v -> new SecurityParkingSlotResponse.VehicleInfo(
                            v.placa(), v.tipo(), v.marca(), v.modelo(), v.color()))
                        .toList()
                    : List.<SecurityParkingSlotResponse.VehicleInfo>of();
                return new SecurityParkingSlotResponse(
                    r.id(), r.numero(),
                    r.tipoVehiculo() != null ? r.tipoVehiculo().name() : null,
                    r.capacidadMaxima(), r.cantidadActual(), r.disponible(),
                    vehiculos);
            })
            .toList();
    }

    public SecurityVehicleVerificationResponse toVehicleVerificationResponse(SecurityVehicleVerificationResult r) {
        return new SecurityVehicleVerificationResponse(
            r.idVehiculo(), r.placa(), r.marca(), r.color(), r.modelo(),
            r.tipo() != null ? r.tipo().name() : null, r.idPropietario(), r.nombrePropietario(),
            r.idEstacionamiento(), r.idEstacionamientoApartamento(),
            r.torreNombre(), r.numeroDepartamento(), r.derechoEstacionamiento());
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

    private static String fmt(Instant i) {
        return i != null ? i.toString() : null;
    }

    public List<SecurityCartLoanFullResponse> toCartLoanFullResponses(List<SecurityCartLoanFullResult> results) {
        return results.stream()
            .map(r -> new SecurityCartLoanFullResponse(
                r.id(), r.solicitante(), r.nombreSolicitante(), r.dniSolicitante(),
                r.codigoCarrito(), r.idApartamento(), r.idCarrito(),
                r.fechaPrestamo(), r.fechaDevolucion(), r.penalizacion()))
            .toList();
    }

    public List<SecurityUnassignedVehicleResponse> toUnassignedVehicleResponses(
            List<SecurityUnassignedVehicleResult> results) {
        return results.stream()
            .map(r -> new SecurityUnassignedVehicleResponse(
                r.idVehiculo(), r.placa(), r.marca(), r.modelo(),
                r.color(), r.tipo(), r.nombrePropietario()))
            .toList();
    }

    public List<SecurityCartAssetResponse> toCartAssetResponses(List<CarritoModel> carritos) {
        return carritos.stream()
            .map(c -> new SecurityCartAssetResponse(
                c.getId(), c.getCodigo(), c.getEstado().name()))
            .toList();
    }

    public List<SecurityApartamentoResponse> toApartamentoResponses(
            List<AdminApartamentoDetailResult> results) {
        return results.stream()
            .map(r -> {
                var inquilinos = r.inquilinos() != null
                    ? r.inquilinos().stream()
                        .map(i -> new SecurityApartamentoResponse.InquilinoInfo(
                            i.id(), i.nombres(), i.apellidos(), i.numeroDocumento()))
                        .toList()
                    : List.<SecurityApartamentoResponse.InquilinoInfo>of();
                return new SecurityApartamentoResponse(
                    r.id(), r.numero(), r.torreNombre(), r.pisoNumero(),
                    r.idPropietario(), r.nombrePropietario(), inquilinos);
            })
            .toList();
    }
}
