package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.CrearNodeCommand;
import com.condominios.sgc.application.dto.result.AdminStructureResult;

public interface GestionarAdminEstructuraUseCase {
    AdminStructureResult obtenerEstructura();
    void crearNodo(CrearNodeCommand cmd);
    void eliminarNodo(Long id, String tipo);
}
