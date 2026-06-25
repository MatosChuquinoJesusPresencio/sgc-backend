package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ForzarCambioContrasenaCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.dto.result.UsuarioGlobalResult;

public interface GestionarUsuariosGlobalUseCase {
    PaginaResult<UsuarioGlobalResult> listar(String search, String rol, Boolean activo, PaginaQuery query);
    void invalidarSesion(Long id);
    void forzarCambioContrasena(Long id, ForzarCambioContrasenaCommand cmd);
}
