package com.condominios.sgc.application.dto;

public record CrearApartamentoRequest(
        Integer numero,
        Boolean derechoEstacionamiento,
        Double metraje,
        Long pisoId,
        String propietarioId)

{}