package com.condominios.sgc.application.impl;

import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.usecase.CrearCondominioUseCase;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;

public class CrearCondominioUseCaseImpl implements CrearCondominioUseCase {
    private final CondominioPort condominioPort;
    private final ConfiguracionPort configuracionPort;

    public CrearCondominioUseCaseImpl(CondominioPort condominioPort, ConfiguracionPort configuracionPort) {
        this.condominioPort = condominioPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public CondominioResponse ejecutar(CrearCondominioCommand command) {
        CondominioModel condominio = new CondominioModel(
            command.nombre(), command.idPais(), command.idCiudad(), command.direccion());

        condominio = condominioPort.guardar(condominio);

        ConfiguracionModel configuracion = ConfiguracionModel.nuevo();

        configuracion.asignarCondominio(condominio.getId());
        configuracionPort.guardar(configuracion);

        return CondominioResponse.desdeModelo(condominio);
    }
}
