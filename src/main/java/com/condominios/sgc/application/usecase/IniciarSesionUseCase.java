package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;

public interface IniciarSesionUseCase {
    SesionUsuario ejecutar(String email, String password);
}
