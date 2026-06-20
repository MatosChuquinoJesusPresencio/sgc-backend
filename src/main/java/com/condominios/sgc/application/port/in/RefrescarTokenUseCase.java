package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;

public interface RefrescarTokenUseCase {
    SesionUsuarioResult ejecutar(String tokenRefresco);
}
