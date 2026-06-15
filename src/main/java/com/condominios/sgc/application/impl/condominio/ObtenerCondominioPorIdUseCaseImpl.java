package com.condominios.sgc.application.impl.condominio;

import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.condominio.ObtenerCondominioPorIdUseCase;
import com.condominios.sgc.domain.exception.CiudadException;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.exception.PaisException;
import com.condominios.sgc.domain.port.CiudadPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.PaisPort;

public class ObtenerCondominioPorIdUseCaseImpl implements ObtenerCondominioPorIdUseCase {
    private final CondominioPort condominioPort;
    private final PaisPort paisPort;
    private final CiudadPort ciudadPort;

    public ObtenerCondominioPorIdUseCaseImpl(CondominioPort condominioPort, PaisPort paisPort, CiudadPort ciudadPort) {
        this.condominioPort = condominioPort;
        this.paisPort = paisPort;
        this.ciudadPort = ciudadPort;
    }

    @Override
    public CondominioResponse ejecutar(Long id) {
        var condominio = condominioPort.obtenerPorId(id)
            .orElseThrow(CondominioException::noEncontrado);

        var nombrePais = paisPort.obtenerPorId(condominio.getIdPais())
                .orElseThrow(PaisException::noEncontrado).getNombre();
        var nombreCiudad = ciudadPort.obtenerPorId(condominio.getIdCiudad())
                .orElseThrow(CiudadException::noEncontrado).getNombre();

        return CondominioResponse.desdeModelo(condominio, nombrePais, nombreCiudad);
    }
}
