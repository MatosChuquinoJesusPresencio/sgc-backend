package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.command.ActualizarMiCondominioCommand;
import com.condominios.sgc.application.dto.result.AdminCondominioInfoResult;
import com.condominios.sgc.application.dto.result.AdminConfiguracionResult;

public interface GestionarAdminCondominioUseCase {
    AdminCondominioInfoResult obtenerMiCondominio();
    AdminCondominioInfoResult actualizarMiCondominio(ActualizarMiCondominioCommand cmd);
    AdminConfiguracionResult obtenerConfiguracion();
    AdminCondominioInfoResult actualizarConfiguracion(ActualizarConfiguracionCommand cmd);
}
