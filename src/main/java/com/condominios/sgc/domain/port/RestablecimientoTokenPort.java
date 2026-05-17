package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.RestablecimientoTokenModel;

import java.util.Optional;

public interface RestablecimientoTokenPort {

    Optional<RestablecimientoTokenModel> findByTokenAndUsadoFalse(String token);

    RestablecimientoTokenModel save(RestablecimientoTokenModel model);
}
