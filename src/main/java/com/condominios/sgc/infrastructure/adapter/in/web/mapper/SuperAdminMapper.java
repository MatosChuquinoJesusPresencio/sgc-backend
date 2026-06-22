package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.AdministradorResult;
import com.condominios.sgc.application.dto.result.CiudadResult;
import com.condominios.sgc.application.dto.result.CondominioResult;
import com.condominios.sgc.application.dto.result.CondominioSimpleResult;
import com.condominios.sgc.application.dto.result.DashboardMetricsResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.dto.result.PaisResult;
import com.condominios.sgc.application.dto.result.UsuarioGlobalResult;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdministradorResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CiudadResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CondominioResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CondominioSimpleResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.DashboardMetricsResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaisResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.UsuarioGlobalResponse;

@Component
public class SuperAdminMapper {

    public AdministradorResponse toAdministradorResponse(AdministradorResult r) {
        return new AdministradorResponse(
            r.id(), r.nombres(), r.apellidos(), r.correo(),
            r.telefono(), r.activo(), r.idCondominio(),
            r.nombreCondominio(), r.fechaCreacion()
        );
    }

    public List<AdministradorResponse> toAdministradorResponses(List<AdministradorResult> results) {
        return results.stream().map(this::toAdministradorResponse).toList();
    }

    public PaginaResponse<AdministradorResponse> toPaginaResponse(PaginaResult<AdministradorResult> pagina) {
        var items = pagina.items().stream().map(this::toAdministradorResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }

    public CondominioSimpleResponse toCondominioSimpleResponse(CondominioSimpleResult r) {
        return new CondominioSimpleResponse(r.id(), r.nombre());
    }

    public List<CondominioSimpleResponse> toCondominioSimpleResponses(List<CondominioSimpleResult> results) {
        return results.stream().map(this::toCondominioSimpleResponse).toList();
    }

    public CondominioResponse toCondominioResponse(CondominioResult r) {
        return new CondominioResponse(
            r.id(), r.nombre(), r.idPais(), r.nombrePais(),
            r.idCiudad(), r.nombreCiudad(), r.direccion(),
            r.activo(), r.idAdministrador(), r.nombreAdministrador(),
            r.fechaCreacion()
        );
    }

    public List<CondominioResponse> toCondominioResponses(List<CondominioResult> results) {
        return results.stream().map(this::toCondominioResponse).toList();
    }

    public PaginaResponse<CondominioResponse> toCondominioPaginaResponse(PaginaResult<CondominioResult> pagina) {
        var items = pagina.items().stream().map(this::toCondominioResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }

    public PaisResponse toPaisResponse(PaisResult r) {
        return new PaisResponse(r.id(), r.nombre(), r.codigoIso());
    }

    public List<PaisResponse> toPaisResponses(List<PaisResult> results) {
        return results.stream().map(this::toPaisResponse).toList();
    }

    public CiudadResponse toCiudadResponse(CiudadResult r) {
        return new CiudadResponse(r.id(), r.nombre(), r.idPais());
    }

    public List<CiudadResponse> toCiudadResponses(List<CiudadResult> results) {
        return results.stream().map(this::toCiudadResponse).toList();
    }

    public DashboardMetricsResponse toDashboardMetricsResponse(DashboardMetricsResult r) {
        return new DashboardMetricsResponse(
            r.totalCondominios(), r.condominiosActivos(),
            r.totalAdministradores(), r.totalPropietarios(),
            r.totalAgentes(), r.totalUsuarios()
        );
    }

    public UsuarioGlobalResponse toUsuarioGlobalResponse(UsuarioGlobalResult r) {
        return new UsuarioGlobalResponse(
            r.id(), r.nombres(), r.apellidos(), r.correo(),
            r.telefono(), r.rol(), r.activo(), r.correoVerificado(),
            r.idCondominio(), r.nombreCondominio(), r.fechaCreacion()
        );
    }

    public List<UsuarioGlobalResponse> toUsuarioGlobalResponses(List<UsuarioGlobalResult> results) {
        return results.stream().map(this::toUsuarioGlobalResponse).toList();
    }

    public PaginaResponse<UsuarioGlobalResponse> toUsuarioGlobalPaginaResponse(PaginaResult<UsuarioGlobalResult> pagina) {
        var items = pagina.items().stream().map(this::toUsuarioGlobalResponse).toList();
        return new PaginaResponse<>(items, pagina.total(), pagina.pagina(),
            pagina.tamano(), pagina.totalPaginas(), pagina.hayMas());
    }
}
