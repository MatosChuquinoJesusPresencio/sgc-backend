package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.command.ActualizarAdminUserCommand;
import com.condominios.sgc.application.dto.command.CrearAdminUserCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminUserResult;
import com.condominios.sgc.application.dto.result.PaginaResult;

public interface GestionarAdminUsuariosUseCase {
    PaginaResult<AdminUserResult> listar(String search, String rol, Boolean activo, PaginaQuery query);
    AdminUserResult crear(CrearAdminUserCommand cmd);
    AdminUserResult actualizar(Long id, ActualizarAdminUserCommand cmd);
}
