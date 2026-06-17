package com.condominios.sgc.application.dto.result;

import java.util.List;

public record DashboardACResult(
    int totalPropietarios,
    int totalApartamentos,
    int apartamentosOcupados,
    int apartamentosDisponibles,
    int totalTorres,
    int totalInquilinos,
    int totalVehiculos,
    int carritosPrestados,
    int accesosHoy,
    List<LogAccesoResumen> ultimosAccesosVehiculares,
    List<CarritoPrestadoResumen> carritosPrestadosAhora,
    List<UsuarioResumen> ultimosPropietarios
) {}
