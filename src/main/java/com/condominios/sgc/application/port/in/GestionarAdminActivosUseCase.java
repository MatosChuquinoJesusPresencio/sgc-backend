package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.result.AdminAssetResult;

public interface GestionarAdminActivosUseCase {
    List<AdminAssetResult> listar();
    AdminAssetResult crear(CrearAssetCommand cmd);
    AdminAssetResult actualizarStatus(Long id, ActualizarStatusAssetCommand cmd);
}
