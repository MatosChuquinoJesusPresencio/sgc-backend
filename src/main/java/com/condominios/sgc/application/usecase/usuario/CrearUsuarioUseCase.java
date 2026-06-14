package com.condominios.sgc.application.usecase.usuario;

import com.condominios.sgc.application.dto.command.CrearUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface CrearUsuarioUseCase {
    UsuarioResponse ejecutar(CrearUsuarioCommand command);
}
