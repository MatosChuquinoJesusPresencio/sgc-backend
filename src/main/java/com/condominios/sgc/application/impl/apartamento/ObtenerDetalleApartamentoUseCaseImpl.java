package com.condominios.sgc.application.impl.apartamento;

import com.condominios.sgc.application.dto.response.DetalleApartamentoResponse;
import com.condominios.sgc.application.dto.response.InquilinoResumen;
import com.condominios.sgc.application.dto.response.EstacionamientoResumen;
import com.condominios.sgc.application.dto.response.PropietarioResumen;
import com.condominios.sgc.application.usecase.apartamento.ObtenerDetalleApartamentoUseCase;
import com.condominios.sgc.domain.exception.ApartamentoException;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.port.InquilinoPort;
import com.condominios.sgc.domain.port.EstacionamientoPort;

public class ObtenerDetalleApartamentoUseCaseImpl implements ObtenerDetalleApartamentoUseCase {

    private final ApartamentoPort apartamentoPort;
    private final PisoPort pisoPort;
    private final TorrePort torrePort;
    private final CondominioPort condominioPort;
    private final UsuarioPort usuarioPort;
    private final InquilinoPort inquilinoPort;
    private final EstacionamientoPort estacionamientoPort;

    public ObtenerDetalleApartamentoUseCaseImpl(ApartamentoPort apartamentoPort, PisoPort pisoPort,
            TorrePort torrePort, CondominioPort condominioPort, UsuarioPort usuarioPort,
            InquilinoPort inquilinoPort, EstacionamientoPort estacionamientoPort) {
        this.apartamentoPort = apartamentoPort;
        this.pisoPort = pisoPort;
        this.torrePort = torrePort;
        this.condominioPort = condominioPort;
        this.usuarioPort = usuarioPort;
        this.inquilinoPort = inquilinoPort;
        this.estacionamientoPort = estacionamientoPort;
    }

    @Override
    public DetalleApartamentoResponse ejecutar(Long id) {
        var apt = apartamentoPort.obtenerPorId(id)
                .orElseThrow(ApartamentoException::noEncontrado);

        PropietarioResumen propietario = null;
        if (apt.getIdPropietario() != null) {
            propietario = usuarioPort.obtenerPorId(apt.getIdPropietario())
                    .map(u -> new PropietarioResumen(u.getId(), u.getNombres(),
                            u.getApellidos(), u.getCorreo(), u.getTelefono()))
                    .orElse(null);
        }

        var piso = pisoPort.obtenerPorId(apt.getIdPiso())
                .orElseThrow(() -> new RuntimeException("Piso no encontrado"));

        var torre = torrePort.obtenerPorId(piso.getIdTorre())
                .orElseThrow(() -> new RuntimeException("Torre no encontrada"));

        var condominio = condominioPort.obtenerPorId(torre.getIdCondominio())
                .orElseThrow(() -> new RuntimeException("Condominio no encontrado"));

        var inquilinos = inquilinoPort.obtenerPorApartamento(id).stream()
                .map(i -> new InquilinoResumen(i.getId(), i.getNombres(), i.getApellidos(),
                        i.getTipoDocumento().name(), i.getNumeroDocumento()))
                .toList();

        var estacionamientos = estacionamientoPort.obtenerPorApartamento(id).stream()
                .map(e -> new EstacionamientoResumen(e.getId(), e.getNumero(),
                        e.getTipoVehiculo() != null ? e.getTipoVehiculo().name() : null,
                        e.getDisponible()))
                .toList();

        return new DetalleApartamentoResponse(
                apt.getId(), apt.getNumero(), apt.getMetraje(), apt.getDerechoEstacionamiento(),
                propietario, piso.getNumero(), torre.getNombre(), condominio.getNombre(),
                inquilinos, estacionamientos);
    }
}
