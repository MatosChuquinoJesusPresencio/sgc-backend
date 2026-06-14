package com.condominios.sgc.application.impl.vehiculo;

import com.condominios.sgc.application.dto.command.CrearVehiculoCommand;
import com.condominios.sgc.application.dto.response.VehiculoResponse;
import com.condominios.sgc.application.usecase.vehiculo.CrearVehiculoUseCase;
import com.condominios.sgc.domain.exception.VehiculoException;
import com.condominios.sgc.domain.model.ConfiguracionModel;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.VehiculoPort;

public class CrearVehiculoUseCaseImpl implements CrearVehiculoUseCase {
    private final VehiculoPort vehiculoPort;
    private final ConfiguracionPort configuracionPort;

    public CrearVehiculoUseCaseImpl(VehiculoPort vehiculoPort, ConfiguracionPort configuracionPort) {
        this.vehiculoPort = vehiculoPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public VehiculoResponse ejecutar(CrearVehiculoCommand command) {
        ConfiguracionModel config = configuracionPort.obtenerPorCondominio(command.idCondominio())
            .orElseThrow(() -> new RuntimeException("configuración no encontrada para el condominio"));
        int count = (int) vehiculoPort.obtenerTodos().stream()
            .filter(v -> command.tipo().equals(v.getTipo())).count();
        if (!config.puedeAgregarVehiculo(command.tipo(), count))
            throw VehiculoException.limiteAlcanzado();

        VehiculoModel vehiculo = new VehiculoModel(
            command.marca(), command.color(), command.modelo(),
            command.placa(), command.tipo());
        vehiculo = vehiculoPort.guardar(vehiculo);
        return VehiculoResponse.desdeModelo(vehiculo);
    }
}
