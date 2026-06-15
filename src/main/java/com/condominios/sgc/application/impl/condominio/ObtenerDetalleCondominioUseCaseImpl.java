package com.condominios.sgc.application.impl.condominio;

import com.condominios.sgc.application.dto.response.CondominioResponse;
import com.condominios.sgc.application.dto.response.ConfiguracionResponse;
import com.condominios.sgc.application.dto.response.DetalleCondominioResponse;
import com.condominios.sgc.application.usecase.condominio.ObtenerDetalleCondominioUseCase;
import com.condominios.sgc.domain.exception.CondominioException;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.CarritoPort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.EstacionamientoPort;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class ObtenerDetalleCondominioUseCaseImpl implements ObtenerDetalleCondominioUseCase {

    private final CondominioPort condominioPort;
    private final TorrePort torrePort;
    private final PisoPort pisoPort;
    private final ApartamentoPort apartamentoPort;
    private final EstacionamientoPort estacionamientoPort;
    private final CarritoPort carritoPort;
    private final UsuarioPort usuarioPort;
    private final ConfiguracionPort configuracionPort;

    public ObtenerDetalleCondominioUseCaseImpl(CondominioPort condominioPort,
                                               TorrePort torrePort,
                                               PisoPort pisoPort,
                                               ApartamentoPort apartamentoPort,
                                               EstacionamientoPort estacionamientoPort,
                                               CarritoPort carritoPort,
                                               UsuarioPort usuarioPort,
                                               ConfiguracionPort configuracionPort) {
        this.condominioPort = condominioPort;
        this.torrePort = torrePort;
        this.pisoPort = pisoPort;
        this.apartamentoPort = apartamentoPort;
        this.estacionamientoPort = estacionamientoPort;
        this.carritoPort = carritoPort;
        this.usuarioPort = usuarioPort;
        this.configuracionPort = configuracionPort;
    }

    @Override
    public DetalleCondominioResponse ejecutar(Long id) {
        var condominio = condominioPort.obtenerPorId(id)
                .map(CondominioResponse::desdeModelo)
                .orElseThrow(CondominioException::noEncontrado);

        var torres = torrePort.obtenerPorCondominio(id);
        int cantidadTorres = torres.size();

        int cantidadPisos = 0;
        for (var torre : torres) {
            cantidadPisos += pisoPort.obtenerPorTorre(torre.getId()).size();
        }

        int cantidadApartamentos = apartamentoPort.contarPorCondominio(id);
        int cantidadEstacionamientos = estacionamientoPort.contarPorCondominio(id);
        int cantidadCarritos = carritoPort.obtenerPorCondominio(id).size();
        int cantidadUsuarios = usuarioPort.obtenerPorCondominio(id).size();

        var configuracion = configuracionPort.obtenerPorCondominio(id)
                .map(ConfiguracionResponse::desdeModelo)
                .orElse(null);

        return new DetalleCondominioResponse(condominio, cantidadTorres, cantidadPisos,
                cantidadApartamentos, cantidadEstacionamientos, cantidadCarritos, cantidadUsuarios, configuracion);
    }
}