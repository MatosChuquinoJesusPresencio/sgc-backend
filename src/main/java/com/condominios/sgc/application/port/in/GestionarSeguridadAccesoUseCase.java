package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.RegistrarEntradaVehiculoCommand;
import com.condominios.sgc.application.dto.command.RegistrarSalidaVehiculoCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminLogEntryResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarSeguridadAccesoUseCase {
    AdminLogEntryResult registrarEntrada(Long condominioIdOverride, RegistrarEntradaVehiculoCommand cmd);
    AdminLogEntryResult registrarSalida(RegistrarSalidaVehiculoCommand cmd);
    PaginaResult<AdminLogEntryResult> listarLogs(Long condominioIdOverride, PaginaQuery pagina);
}
