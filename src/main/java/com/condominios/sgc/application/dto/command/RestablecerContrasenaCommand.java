package com.condominios.sgc.application.dto.command;

public record RestablecerContrasenaCommand(
    String tokenRestablecimiento,
    String nuevaContrasena
) {}
