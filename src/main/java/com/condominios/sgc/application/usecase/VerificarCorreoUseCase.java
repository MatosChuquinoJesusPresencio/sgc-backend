package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface VerificarCorreoUseCase {
    UsuarioModel ejecutar(String token);
}
