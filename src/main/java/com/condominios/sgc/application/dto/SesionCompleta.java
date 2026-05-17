package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.SesionUsuario;
import com.condominios.sgc.domain.model.UsuarioModel;

public record SesionCompleta(
    SesionUsuario sesion,
    UsuarioModel usuario
) {}
