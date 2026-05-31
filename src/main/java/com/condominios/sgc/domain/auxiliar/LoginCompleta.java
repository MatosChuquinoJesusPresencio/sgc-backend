package com.condominios.sgc.domain.auxiliar;

import com.condominios.sgc.domain.model.UsuarioModel;

public record LoginCompleta(SesionUsuario sesion, UsuarioModel usuario) {
}
