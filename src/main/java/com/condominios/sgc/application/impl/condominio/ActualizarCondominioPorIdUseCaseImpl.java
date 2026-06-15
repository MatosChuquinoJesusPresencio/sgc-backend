package com.condominios.sgc.application.impl.condominio;

import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.condominio.ActualizarCondominioPorIdUseCase;
import com.condominios.sgc.domain.exception.CiudadException;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.exception.PaisException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CiudadPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.PaisPort;

public class ActualizarCondominioPorIdUseCaseImpl implements ActualizarCondominioPorIdUseCase {
    private final CondominioPort condominioPort;
    private final PaisPort paisPort;
    private final CiudadPort ciudadPort;

    public ActualizarCondominioPorIdUseCaseImpl(CondominioPort condominioPort, PaisPort paisPort, CiudadPort ciudadPort) {
        this.condominioPort = condominioPort;
        this.paisPort = paisPort;
        this.ciudadPort = ciudadPort;
    }

    @Override
    public CondominioResponse ejecutar(Long id, ActualizarCondominioCommand command) {
        CondominioModel condominio = condominioPort.obtenerPorId(id)
            .orElseThrow(CondominioException::noEncontrado);

        condominio.actualizar(command.nombre(), command.idPais(), command.idCiudad(), command.direccion());
        condominio = condominioPort.guardar(condominio);

        var nombrePais = paisPort.obtenerPorId(condominio.getIdPais())
                .orElseThrow(PaisException::noEncontrado).getNombre();
        var nombreCiudad = ciudadPort.obtenerPorId(condominio.getIdCiudad())
                .orElseThrow(CiudadException::noEncontrado).getNombre();

        return CondominioResponse.desdeModelo(condominio, nombrePais, nombreCiudad);
    }
}
