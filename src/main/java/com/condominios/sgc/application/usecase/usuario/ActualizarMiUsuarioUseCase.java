package com.condominios.sgc.application.usecase.usuario;

import com.condominios.sgc.application.dto.command.ActualizarMiUsuarioCommand;
import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface ActualizarMiUsuarioUseCase {
    UsuarioResponse ejecutar(Long id, ActualizarMiUsuarioCommand command);
}
