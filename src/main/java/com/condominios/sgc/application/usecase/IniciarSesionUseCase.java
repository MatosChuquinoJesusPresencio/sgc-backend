package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.SesionUsuario;

public interface IniciarSesionUseCase {
    SesionUsuario ejecutar(String email, String password);
}
