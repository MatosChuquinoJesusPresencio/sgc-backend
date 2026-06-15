package com.condominios.sgc.application.impl.condominio;

import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import org.springframework.transaction.annotation.Transactional;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.condominio.CrearCondominioUseCase;
import com.condominios.sgc.domain.exception.CiudadException;
import com.condominios.sgc.domain.exception.PaisException;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.CiudadPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.PaisPort;

@Transactional
public class CrearCondominioUseCaseImpl implements CrearCondominioUseCase {
    private final CondominioPort condominioPort;
    private final ConfiguracionPort configuracionPort;
    private final PaisPort paisPort;
    private final CiudadPort ciudadPort;

    public CrearCondominioUseCaseImpl(CondominioPort condominioPort, ConfiguracionPort configuracionPort,
                                      PaisPort paisPort, CiudadPort ciudadPort) {
        this.condominioPort = condominioPort;
        this.configuracionPort = configuracionPort;
        this.paisPort = paisPort;
        this.ciudadPort = ciudadPort;
    }

    @Override
    public CondominioResponse ejecutar(CrearCondominioCommand command) {
        CondominioModel condominio = new CondominioModel(
            command.nombre(), command.idPais(), command.idCiudad(), command.direccion());

        condominio = condominioPort.guardar(condominio);

        ConfiguracionModel configuracion = ConfiguracionModel.nuevo();

        configuracion.asignarCondominio(condominio.getId());
        configuracionPort.guardar(configuracion);

        var nombrePais = paisPort.obtenerPorId(condominio.getIdPais())
                .orElseThrow(PaisException::noEncontrado).getNombre();
        var nombreCiudad = ciudadPort.obtenerPorId(condominio.getIdCiudad())
                .orElseThrow(CiudadException::noEncontrado).getNombre();

        return CondominioResponse.desdeModelo(condominio, nombrePais, nombreCiudad);
    }
}
