package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.LoginCompleta;

public interface RefrescarTokenUseCase {
    LoginCompleta ejecutar(String refreshToken);
}
