package com.condominios.sgc.application.dto;

public record CrearInquilinoRequest(
    String nombres,
    String apellidos,
    String dni,
    Long apartamentoId
) {}
