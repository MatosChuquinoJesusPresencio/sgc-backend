package com.condominios.sgc.application.dto;
public record ActualizarApartamentoRequest(

        Integer numero,
        Boolean derechoEstacionamiento,
        Double metraje,
        String propietarioId)

{}