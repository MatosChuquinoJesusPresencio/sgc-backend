package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;

public interface RefrescarTokenUseCase {
    SesionUsuario ejecutar(String refreshToken);
}
