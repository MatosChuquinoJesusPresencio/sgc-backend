package com.condominios.sgc.web.dto.response;

public record MeResponse(
    Long idUsuario,
    String correo,
    String nombres,
    String apellidos,
    String rol,
    Long idCondominio
) {}