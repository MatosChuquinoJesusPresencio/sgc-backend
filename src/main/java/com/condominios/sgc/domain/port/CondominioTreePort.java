package com.condominios.sgc.domain.port;

import com.condominios.sgc.web.dto.CondominioTreeResponse;

public interface CondominioTreePort {
    CondominioTreeResponse obtenerArbolCompleto(Long condominioId);
}