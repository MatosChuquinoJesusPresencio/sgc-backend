package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarMiCondominioCommand;
import com.condominios.sgc.application.dto.result.AdminCondominioInfoResult;

public interface GestionarAdminCondominioUseCase {
    AdminCondominioInfoResult obtenerMiCondominio();
    AdminCondominioInfoResult actualizarMiCondominio(ActualizarMiCondominioCommand cmd);
}
