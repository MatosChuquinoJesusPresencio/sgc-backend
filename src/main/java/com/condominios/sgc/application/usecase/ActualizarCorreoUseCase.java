package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface ActualizarCorreoUseCase {
    UsuarioModel ejecutar(String id, String accessToken, String nuevoCorreo);
}
