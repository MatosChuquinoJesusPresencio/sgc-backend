package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface ObtenerUsuarioUseCase {
    UsuarioModel ejecutar(String id);
}
