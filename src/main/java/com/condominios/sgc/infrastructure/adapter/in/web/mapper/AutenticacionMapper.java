package com.condominios.sgc.infrastructure.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.result.SesionUsuarioResult;
import com.condominios.sgc.application.dto.result.UsuarioActualResult;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.AutenticacionResponse;
import com.condominios.sgc.infrastructure.adapter.in.web.dto.response.UsuarioResponse;

@Component
public class AutenticacionMapper {

    public AutenticacionResponse toAutenticacionResponse(SesionUsuarioResult resultado) {
        var u = resultado.usuarioActual();
        var usuarioResponse = new UsuarioResponse(
            u.id(), u.nombres(), u.apellidos(), u.rol(), u.idCondominio());
        return new AutenticacionResponse(usuarioResponse, resultado.expiracionAccesoMs(), resultado.expiracionRefrescoMs());
    }

    public UsuarioResponse toUsuarioResponse(UsuarioActualResult u) {
        return new UsuarioResponse(
            u.id(), u.nombres(), u.apellidos(), u.rol(), u.idCondominio());
    }
}
