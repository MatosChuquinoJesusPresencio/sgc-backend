package com.condominios.sgc.domain.shared.valueobject;

import com.condominios.sgc.domain.shared.exception.ValueObjectException;

public record NombreCompleto(String nombres, String apellidos) {
    public NombreCompleto {
        if (nombres == null || nombres.isBlank())
            throw ValueObjectException.nombresRequeridos();
        if (apellidos == null || apellidos.isBlank())
            throw ValueObjectException.apellidosRequeridos();
    }

    public String formatoCompleto() {
        return nombres + " " + apellidos;
    }
}
