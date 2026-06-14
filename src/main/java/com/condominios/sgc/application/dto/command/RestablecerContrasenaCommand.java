package com.condominios.sgc.application.dto.command;

public record RestablecerContrasenaCommand(
    String token,
    String nuevaContrasena
) {}
