package com.condominios.sgc.application.dto.command;

public record CambiarContrasenaCommand(
    Long idUsuario,
    String contrasenaActual,
    String nuevaContrasena
) {}
