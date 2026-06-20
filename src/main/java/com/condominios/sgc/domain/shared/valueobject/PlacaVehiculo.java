package com.condominios.sgc.domain.shared.valueobject;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;

public record PlacaVehiculo(String valor) {
    public PlacaVehiculo {
        if (valor == null || valor.isBlank())
            throw ValueObjectException.placaRequerida();
    }
}
