package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.command.ActualizarMiCondominioCommand;
import com.condominios.sgc.application.dto.result.AdminCondominioInfoResult;
import com.condominios.sgc.application.dto.result.AdminConfiguracionResult;

public interface GestionarAdminCondominioUseCase {
    AdminCondominioInfoResult obtenerMiCondominio(Long condominioIdOverride);
    AdminCondominioInfoResult actualizarMiCondominio(Long condominioIdOverride, ActualizarMiCondominioCommand cmd);
    AdminConfiguracionResult obtenerConfiguracion(Long condominioIdOverride);
    AdminCondominioInfoResult actualizarConfiguracion(Long condominioIdOverride, ActualizarConfiguracionCommand cmd);
}
