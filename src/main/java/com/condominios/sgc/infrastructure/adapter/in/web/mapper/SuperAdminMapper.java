package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.AdministradorResult;
import com.condominios.sgc.application.dto.result.CondominioSimpleResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AdministradorResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.CondominioSimpleResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.PaginaResponse;

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
}
