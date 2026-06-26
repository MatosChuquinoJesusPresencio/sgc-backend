package com.condominios.sgc.domain.shared.valueobject;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;

public record Telefono(String numero) {

    public Telefono {
        if (numero == null || numero.isBlank()) {
            throw ValueObjectException.telefonoRequerido();
        }

        numero = numero.trim();

        if (!numero.matches("^\\+?[1-9]\\d{6,14}$")) {
            throw ValueObjectException.telefonoInvalido();
        }
    }
}