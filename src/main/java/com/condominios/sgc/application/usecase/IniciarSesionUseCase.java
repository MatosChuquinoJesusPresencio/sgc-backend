package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.auxiliar.LoginCompleta;

public interface IniciarSesionUseCase {
    LoginCompleta ejecutar(String email, String password, boolean rememberMe);
}
