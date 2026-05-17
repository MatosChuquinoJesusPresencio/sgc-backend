package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface ActualizarCorreoAdminUseCase {
    UsuarioModel ejecutar(String id, String nuevoCorreo);
}
