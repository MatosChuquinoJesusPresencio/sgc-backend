package com.condominios.sgc.application.usecase.usuario;

import com.condominios.sgc.application.dto.command.ActualizarUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface ActualizarUsuarioPorIdUseCase {
    UsuarioResponse ejecutar(Long id, ActualizarUsuarioCommand command);
}
