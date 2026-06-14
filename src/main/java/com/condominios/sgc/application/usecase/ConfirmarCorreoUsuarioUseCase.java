package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.response.UsuarioResponse;

public interface ConfirmarCorreoUsuarioUseCase {
    UsuarioResponse ejecutar(Long id);
}
