package com.condominios.sgc.domain.filter;

import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.auxiliar.MetodoEntrada;
import java.time.Instant;

public record LogAccesoVehicularFilter(
    String placa,
    TipoHabitante ocupante,
    MetodoEntrada metodo,
    Long idVehiculo,
    Long idEstacionamiento,
    Instant fechaDesde, Instant
    fechaHasta,
    Boolean sinSalida
) {}

