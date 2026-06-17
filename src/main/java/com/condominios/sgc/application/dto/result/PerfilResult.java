package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record PerfilResult(
    Long id,
    String nombres,
    String apellidos,
    String correo,
    String telefono,
    String rol,
    boolean activo,
    boolean correoVerificado,
    Long idCondominio,
    Instant fechaCreacion
) {}
