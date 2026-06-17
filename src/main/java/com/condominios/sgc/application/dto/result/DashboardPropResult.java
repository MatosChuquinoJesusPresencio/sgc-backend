package com.condominios.sgc.application.dto.result;

import java.util.List;

public record DashboardPropResult(
    int misVehiculos,
    int misInquilinos,
    int carritosPrestadosAhora,
    List<InquilinoResumen> inquilinos,
    List<LogAccesoResumen> accesosVehicularesHoy
) {}
