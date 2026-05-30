package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.model.RestablecimientoTokenModel;

public interface RestablecimientoTokenPort {

    Optional<RestablecimientoTokenModel> findByTokenAndUsadoFalse(String token);

    RestablecimientoTokenModel save(RestablecimientoTokenModel model);
}
