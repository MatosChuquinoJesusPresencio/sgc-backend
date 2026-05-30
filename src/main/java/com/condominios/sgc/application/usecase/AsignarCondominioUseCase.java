package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface AsignarCondominioUseCase {
    UsuarioModel ejecutar(String usuarioId, Long condominioId);
}
