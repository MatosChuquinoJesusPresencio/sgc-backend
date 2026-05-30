package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface ActualizarEstadoUsuarioUseCase {
    UsuarioModel ejecutar(String id, Boolean activo, Rol rolAutenticado);
}
