package com.condominios.sgc.infrastructure.adapter.out.persistence.repository;

import java.time.Instant;

public interface LogCombinadoProjection {
    String getTipoLog();
    String getIdentificador();
    String getUsuario();
    Instant getFecha();
}
