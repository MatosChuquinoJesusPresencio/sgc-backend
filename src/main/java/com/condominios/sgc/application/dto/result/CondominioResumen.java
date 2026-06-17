package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record CondominioResumen(
    Long id,
    String nombre,
    String direccion,
    Instant fechaCreacion
) {}
