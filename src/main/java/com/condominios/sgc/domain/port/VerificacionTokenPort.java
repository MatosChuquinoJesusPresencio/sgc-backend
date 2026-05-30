package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.model.VerificacionTokenModel;

public interface VerificacionTokenPort {
    Optional<VerificacionTokenModel> findByTokenAndUsadoFalse(String token);
    VerificacionTokenModel save(VerificacionTokenModel model);
}
