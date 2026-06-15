package com.condominios.sgc.application.impl.logprestamocarrito;

import com.condominios.sgc.application.dto.command.CrearLogPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.response.LogPrestamoCarritoResponse;
import com.condominios.sgc.application.usecase.logprestamocarrito.CrearLogPrestamoCarritoUseCase;
import com.condominios.sgc.domain.auxiliar.TipoHabitante;
import com.condominios.sgc.domain.exception.ConfiguracionException;
import com.condominios.sgc.domain.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;

public class CrearLogPrestamoCarritoUseCaseImpl implements CrearLogPrestamoCarritoUseCase {
    private final LogPrestamoCarritoPort logPrestamoCarritoPort;
    private final ConfiguracionPort configuracionPort;

    public CrearLogPrestamoCarritoUseCaseImpl(LogPrestamoCarritoPort logPrestamoCarritoPort, ConfiguracionPort configuracionPort) {
        this.logPrestamoCarritoPort = logPrestamoCarritoPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public LogPrestamoCarritoResponse ejecutar(CrearLogPrestamoCarritoCommand command) {
        ConfiguracionModel config = configuracionPort.obtenerPorCondominio(command.idCondominio())
            .orElseThrow(ConfiguracionException::noEncontrado);
        int count = (int) logPrestamoCarritoPort.contarSinDevolucionPorApartamento(command.idApartamento());
        if (!config.puedeUsarCarrito(count))
            throw LogPrestamoCarritoException.limiteAlcanzado();

        TipoHabitante solicitante = command.idPropietario() != null
            ? TipoHabitante.PROPIETARIO : TipoHabitante.INQUILINO;

        LogPrestamoCarritoModel log = new LogPrestamoCarritoModel(
            solicitante, command.nombreSolicitante(), command.dniSolicitante(),
            command.idApartamento(), command.idCarrito());

        if (command.idPropietario() != null) {
            log.asignarPropietario(command.idPropietario());
        } else {
            log.asignarInquilino(command.idInquilino());
        }

        log = logPrestamoCarritoPort.guardar(log);

        return LogPrestamoCarritoResponse.desdeModelo(log);
    }
}
