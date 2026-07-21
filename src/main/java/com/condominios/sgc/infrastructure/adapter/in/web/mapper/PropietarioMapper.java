package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.PropietarioApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PropietarioDashboardResult;
import com.condominios.sgc.application.dto.result.PropietarioEstacionamientoResult;
import com.condominios.sgc.application.dto.result.PropietarioInquilinoResult;
import com.condominios.sgc.application.dto.result.PropietarioVehiculoResult;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioApartamentoDetailResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioEstacionamientoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioInquilinoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PropietarioVehiculoResponse;

@Component
public class PropietarioMapper {

    public PropietarioDashboardResult toDashboardResult(
            Long idApartamento, Integer numeroApartamento,
            String torreNombre, Integer pisoNumero,
            int totalInquilinos, int totalVehiculos) {
        return new PropietarioDashboardResult(
            idApartamento, numeroApartamento, torreNombre, pisoNumero,
            totalInquilinos, totalVehiculos, 0);
    }

    public PropietarioInquilinoResponse toInquilinoResponse(PropietarioInquilinoResult r) {
        return new PropietarioInquilinoResponse(
            r.id(), r.nombres(), r.apellidos(),
            r.tipoDocumento(), r.numeroDocumento());
    }

    public List<PropietarioInquilinoResponse> toInquilinoResponses(List<PropietarioInquilinoResult> results) {
        return results.stream().map(this::toInquilinoResponse).toList();
    }

    public PropietarioVehiculoResponse toVehiculoResponse(PropietarioVehiculoResult r) {
        return new PropietarioVehiculoResponse(
            r.id(), r.marca(), r.color(), r.modelo(),
            r.placa(), r.tipo(), r.idEstacionamiento(),
            r.nombreInquilino(), r.esDelPropietario());
    }

    public List<PropietarioVehiculoResponse> toVehiculoResponses(List<PropietarioVehiculoResult> results) {
        return results.stream().map(this::toVehiculoResponse).toList();
    }

    public PropietarioEstacionamientoResponse toEstacionamientoResponse(PropietarioEstacionamientoResult r) {
        return new PropietarioEstacionamientoResponse(
            r.id(), r.numero(), r.tipoVehiculo(),
            r.capacidadMaxima(), r.cantidadActual(), r.disponible());
    }

    public List<PropietarioEstacionamientoResponse> toEstacionamientoResponses(List<PropietarioEstacionamientoResult> results) {
        return results.stream().map(this::toEstacionamientoResponse).toList();
    }

    public PropietarioApartamentoDetailResponse toApartamentoDetailResponse(PropietarioApartamentoDetailResult r) {
        var inquilinos = r.inquilinos().stream().map(this::toInquilinoResponse).toList();
        var vehiculos = r.vehiculos().stream().map(this::toVehiculoResponse).toList();
        return new PropietarioApartamentoDetailResponse(
            r.id(), r.numero(), r.metraje(), r.derechoEstacionamiento(),
            r.torreNombre(), r.pisoNumero(),
            r.totalInquilinos(), r.totalVehiculos(),
            inquilinos, vehiculos);
    }
}
