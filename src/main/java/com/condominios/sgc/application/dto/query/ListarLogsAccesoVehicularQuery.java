package com.condominios.sgc.application.dto.query;

import java.time.Instant;

import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;

public record ListarLogsAccesoVehicularQuery(
    int pagina,
    int tamano,
    String placa,
    TipoHabitante ocupante,
    MetodoEntrada metodo,
    Long idVehiculo,
    Long idEstacionamiento,
    Instant fechaDesde,
    Instant fechaHasta,
    Boolean sinSalida
) {}
