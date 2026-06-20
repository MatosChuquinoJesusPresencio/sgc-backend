package com.condominios.sgc.application.port.in;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;

public interface IniciarSesionUseCase {
    SesionUsuarioResult ejecutar(String correo, String contrasena, Boolean recuerdame);
}
