package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import java.time.Instant;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.AdminApartamentoResult;
import com.condominios.sgc.application.dto.result.AdminAssetResult;
import com.condominios.sgc.application.dto.result.AdminCondominioInfoResult;
import com.condominios.sgc.application.dto.result.AdminConfiguracionResult;
import com.condominios.sgc.application.dto.result.AdminDashboardMetricsResult;
import com.condominios.sgc.application.dto.result.AdminInquilinoResult;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.AdminPisoResult;
import com.condominios.sgc.application.dto.result.AdminStructureResult;
import com.condominios.sgc.application.dto.result.AdminTorreResult;
import com.condominios.sgc.application.dto.result.AdminUserResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminApartamentoDetailResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminApartamentoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminAssetResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminCondominioInfoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminConfiguracionResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminDashboardMetricsResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminInquilinoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminLogEntryResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminPisoResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminStructureResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminTorreResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdminUserResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;

@Component
public class AdminCondominioMapper {

    public AdminDashboardMetricsResponse toDashboardMetricsResponse(AdminDashboardMetricsResult r) {
        return new AdminDashboardMetricsResponse(
            r.totalTorres(), r.totalPisos(), r.totalApartamentos(),
            r.totalPropietarios(), r.totalAgentes(),
            r.totalVehiculos(), r.totalCarritos()
        );
    }

    public AdminCondominioInfoResponse toCondominioInfoResponse(AdminCondominioInfoResult r) {
        return new AdminCondominioInfoResponse(
            r.id(), r.nombre(), r.idPais(), r.nombrePais(),
            r.idCiudad(), r.nombreCiudad(), r.direccion(),
            r.activo(), toConfiguracionResponse(r.configuracion())
        );
    }

    public AdminConfiguracionResponse toConfiguracionResponse(AdminConfiguracionResult r) {
        return new AdminConfiguracionResponse(
            r.maxAutos(), r.maxMotos(), r.penalizacionPorMin(),
            r.maxTiempoPrestamoMin(), r.maxEstacionamientosPorDepto(),
            r.maxCarritosPorDepto(), r.maxVehiculosPorDepto(), r.maxInquilinosPorDepto()
        );
    }

    public AdminStructureResponse toStructureResponse(AdminStructureResult r) {
        var torres = r.torres().stream().map(this::toTorreResponse).toList();
        return new AdminStructureResponse(r.condominioId(), r.condominioNombre(), torres);
    }

    private AdminTorreResponse toTorreResponse(AdminTorreResult r) {
        var pisos = r.pisos().stream().map(this::toPisoResponse).toList();
        return new AdminTorreResponse(r.id(), r.nombre(), pisos);
    }

    private AdminPisoResponse toPisoResponse(AdminPisoResult r) {
        var aptos = r.apartamentos().stream().map(this::toApartamentoResponse).toList();
        return new AdminPisoResponse(r.id(), r.numero(), aptos);
    }

    private AdminApartamentoResponse toApartamentoResponse(AdminApartamentoResult r) {
        return new AdminApartamentoResponse(
            r.id(), r.numero(), r.metraje(),
            r.derechoEstacionamiento(), r.idPropietario()
        );
    }

    public AdminUserResponse toUserResponse(AdminUserResult r) {
        return new AdminUserResponse(
            r.id(), r.nombres(), r.apellidos(), r.correo(),
            r.telefono(), r.rol(), r.activo(), r.fechaCreacion()
        );
    }

    public PaginaResponse<AdminUserResponse> toUserPaginaResponse(PaginaResult<AdminUserResult> pagina) {
        var items = pagina.items().stream().map(this::toUserResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }

    public AdminApartamentoDetailResponse toApartamentoDetailResponse(AdminApartamentoDetailResult r) {
        var inquilinos = r.inquilinos().stream().map(this::toInquilinoResponse).toList();
        return new AdminApartamentoDetailResponse(
            r.id(), r.numero(), r.metraje(), r.derechoEstacionamiento(),
            r.idPropietario(), r.nombrePropietario(),
            r.torreNombre(), r.pisoNumero(), inquilinos);
    }

    public PaginaResponse<AdminApartamentoDetailResponse> toApartamentoDetailPaginaResponse(
            PaginaResult<AdminApartamentoDetailResult> pagina) {
        var items = pagina.items().stream().map(this::toApartamentoDetailResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }

    private AdminInquilinoResponse toInquilinoResponse(AdminInquilinoResult r) {
        return new AdminInquilinoResponse(
            r.id(), r.nombres(), r.apellidos(),
            r.tipoDocumento(), r.numeroDocumento());
    }

    public AdminAssetResponse toAssetResponse(AdminAssetResult r) {
        return new AdminAssetResponse(
            r.id(), r.tipo(), r.codigo(), r.estado(),
            r.numero(), r.tipoVehiculo(), r.capacidadMaxima(),
            r.cantidadActual(), r.disponible(), r.idApartamento(),
            r.idCondominio()
        );
    }

    public PaginaResponse<AdminAssetResponse> toAssetPaginaResponse(PaginaResult<AdminAssetResult> pagina) {
        var items = pagina.items().stream().map(this::toAssetResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }

    public AdminLogEntryResponse toLogEntryResponse(AdminLogEntryResult r) {
        return new AdminLogEntryResponse(
            r.id(), r.tipo(),
            r.placa(), r.ocupante(), r.datosInquilino(), r.metodo(),
            fmt(r.fechaEntrada()), fmt(r.fechaSalida()),
            r.solicitante(), r.nombreSolicitante(), r.dniSolicitante(),
            r.penalizacion(), fmt(r.fechaPrestamo()), fmt(r.fechaDevolucion()),
            r.idCondominio());
    }

    private static String fmt(Instant i) {
        return i != null ? i.toString() : null;
    }

    public PaginaResponse<AdminLogEntryResponse> toLogEntryPaginaResponse(PaginaResult<AdminLogEntryResult> pagina) {
        var items = pagina.items().stream().map(this::toLogEntryResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }
}
