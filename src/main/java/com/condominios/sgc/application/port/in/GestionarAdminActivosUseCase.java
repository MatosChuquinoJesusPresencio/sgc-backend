package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminAssetResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdminActivosUseCase {
    PaginaResult<AdminAssetResult> listar(String type, PaginaQuery pagina);
    AdminAssetResult crear(CrearAssetCommand cmd);
    AdminAssetResult actualizarStatus(Long id, ActualizarStatusAssetCommand cmd);
}
