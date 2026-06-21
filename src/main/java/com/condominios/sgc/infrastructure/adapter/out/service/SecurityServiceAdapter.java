package com.condominios.sgc.infrastructure.adapter.out.service;

import org.springframework.stereotype.Component;

import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.infrastructure.adapter.out.util.SecurityUtil;

@Component
public class SecurityServiceAdapter implements SecurityServicePort {

    private final SecurityUtil securityUtil;

    public SecurityServiceAdapter(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Override
    public Long obtenerIdUsuario() {
        return securityUtil.obtenerIdUsuario();
    }

    @Override
    public Rol obtenerRolAutenticado() {
        return securityUtil.obtenerRolAutenticado();
    }
}
