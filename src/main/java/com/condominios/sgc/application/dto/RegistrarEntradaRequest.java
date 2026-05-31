package com.condominios.sgc.application.dto;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarEntradaRequest(
    @NotBlank String placa,
    @NotNull TipoHabitante ocupante,
    String datosInquilino,
    @NotNull MetodoEntrada metodo,
    Long vehiculoId,
    Long estacionamientoId
) {}