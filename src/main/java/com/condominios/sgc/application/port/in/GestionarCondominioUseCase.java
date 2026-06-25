package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.CondominioResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarCondominioUseCase {
    PaginaResult<CondominioResult> listar(String search, Boolean activo, PaginaQuery query);
    CondominioResult crear(CrearCondominioCommand cmd);
    CondominioResult actualizar(Long id, ActualizarCondominioCommand cmd);
    void eliminar(Long id);
    void activarDesactivar(Long id, Boolean activo);
}
