package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.ActualizarUsuarioRequest;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface ActualizarUsuarioUseCase {
    UsuarioModel ejecutar(Long id, ActualizarUsuarioRequest request, Rol rolAutenticado);
}
