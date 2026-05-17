package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.SesionCompleta;

public interface IniciarSesionUseCase {
    SesionCompleta ejecutar(String email, String password);
}
