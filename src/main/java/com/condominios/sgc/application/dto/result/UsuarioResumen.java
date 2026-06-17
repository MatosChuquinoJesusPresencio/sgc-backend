package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record UsuarioResumen(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String rol,
    boolean activo,
    Instant fechaCreacion
) {}
