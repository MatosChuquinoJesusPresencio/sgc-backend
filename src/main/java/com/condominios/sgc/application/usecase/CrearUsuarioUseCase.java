package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.CrearUsuarioRequest;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface CrearUsuarioUseCase {
    UsuarioModel ejecutar(CrearUsuarioRequest request, Rol rolAutenticado);
}
