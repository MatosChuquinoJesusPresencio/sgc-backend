package com.condominios.sgc.application.dto.command;

public record CambiarContrasenaCommand(
    String contrasenaActual,
    String nuevaContrasena
) {}
