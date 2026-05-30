package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.VerificacionTokenModel;

import java.util.Optional;

public interface VerificacionTokenPort {
    Optional<VerificacionTokenModel> findByTokenAndUsadoFalse(String token);
    VerificacionTokenModel save(VerificacionTokenModel model);
}
