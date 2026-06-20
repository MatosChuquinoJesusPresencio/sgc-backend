package com.condominios.sgc.application.dto.command;

public record IniciarSesionCommand(
    String correo,
    String contrasena,
    Boolean recuerdame
) {}
