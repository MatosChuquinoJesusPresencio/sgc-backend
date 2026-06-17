package com.condominios.sgc.application.dto.result;

import java.time.Instant;

public record LogAccesoResumen(
    Long id,
    String placa,
    String ocupante,
    String metodo,
    Instant fechaEntrada,
    Instant fechaSalida
) {}
