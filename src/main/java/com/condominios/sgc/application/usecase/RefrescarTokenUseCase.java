package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.SesionUsuario;

public interface RefrescarTokenUseCase {
    SesionUsuario ejecutar(String refreshToken);
}
