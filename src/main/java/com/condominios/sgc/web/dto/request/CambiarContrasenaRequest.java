package com.condominios.sgc.web.dto.request;

public record CambiarContrasenaRequest(
    String contrasenaActual,
    String nuevaContrasena
) {}
