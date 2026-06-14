package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.command.CrearUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface CrearUsuarioUseCase {
    UsuarioResponse ejecutar(CrearUsuarioCommand command);
}
