package com.condominios.sgc.web.dto.request;

public record RestablecerContrasenaRequest(
    String token,
    String nuevaContrasena
) {}
