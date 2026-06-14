package com.condominios.sgc.application.dto.command;

public record LoginCommand(
    String correo,
    String contrasena
) {}
