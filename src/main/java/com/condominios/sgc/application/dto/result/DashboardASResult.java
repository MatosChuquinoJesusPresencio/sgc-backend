package com.condominios.sgc.application.dto.result;

import java.util.List;

public record DashboardASResult(
    int accesosHoy,
    int vehiculosDentro,
    int carritosPrestados,
    int estacionamientosDisponibles,
    List<LogAccesoResumen> ultimosAccesosVehiculares,
    List<CarritoPrestadoResumen> carritosPrestadosAhora
) {}
