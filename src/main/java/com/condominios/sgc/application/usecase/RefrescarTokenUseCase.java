package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.SesionCompleta;

public interface RefrescarTokenUseCase {
    SesionCompleta ejecutar(String refreshToken);
}
