package com.condominios.sgc.application.dto.command;

public record ReestablecerContrasenaCommand(
    String token,
    String nuevaContrasena
) {}
