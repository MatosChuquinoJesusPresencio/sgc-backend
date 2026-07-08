package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.CrearNodeCommand;
import com.condominios.sgc.application.dto.result.AdminStructureResult;

public interface GestionarAdminEstructuraUseCase {
    AdminStructureResult obtenerEstructura(Long condominioIdOverride);
    void crearNodo(Long condominioIdOverride, CrearNodeCommand cmd);
    void eliminarNodo(Long condominioIdOverride, Long id, String tipo);
}
