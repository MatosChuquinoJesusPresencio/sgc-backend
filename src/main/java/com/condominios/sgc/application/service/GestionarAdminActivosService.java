package com.condominios.sgc.application.service;

import java.util.ArrayList;
import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.result.AdminAssetResult;
import com.condominios.sgc.application.port.in.GestionarAdminActivosUseCase;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.shared.exception.CarritoException;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.EstacionamientoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.EstadoCarrito;
import com.condominios.sgc.domain.type.TipoVehiculo;
import org.springframework.transaction.annotation.Transactional;

public class GestionarAdminActivosService implements GestionarAdminActivosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CarritoRepositoryPort carritoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;

    public GestionarAdminActivosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CarritoRepositoryPort carritoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    private Long obtenerCondominioId() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        return condominioId;
    }

    @Override
    public List<AdminAssetResult> listar() {
        var condominioId = obtenerCondominioId();
        var resultados = new ArrayList<AdminAssetResult>();

        for (var c : carritoRepository.buscarPorCondominio(condominioId)) {
            resultados.add(toResult(c));
        }
        for (var e : estacionamientoRepository.buscarPorCondominio(condominioId)) {
            resultados.add(toResult(e));
        }

        return resultados;
    }

    @Transactional
    @Override
    public AdminAssetResult crear(CrearAssetCommand cmd) {
        var condominioId = obtenerCondominioId();

        if ("CARRITO".equalsIgnoreCase(cmd.tipo())) {
            var modelo = new CarritoModel(cmd.codigo(), condominioId);
            return toResult(carritoRepository.guardar(modelo));
        } else if ("ESTACIONAMIENTO".equalsIgnoreCase(cmd.tipo())) {
            var modelo = new EstacionamientoModel(cmd.numero(), condominioId);
            return toResult(estacionamientoRepository.guardar(modelo));
        }
        throw new IllegalArgumentException("Tipo de activo no válido: " + cmd.tipo());
    }

    @Override
    public AdminAssetResult actualizarStatus(Long id, ActualizarStatusAssetCommand cmd) {
        var condominioId = obtenerCondominioId();

        if ("CARRITO".equalsIgnoreCase(cmd.tipo())) {
            var carrito = carritoRepository.buscarPorId(id)
                .orElseThrow(CarritoException::noEncontrado);
            if (!carrito.getIdCondominio().equals(condominioId)) {
                throw CondominioException.noEncontrado();
            }
            if (cmd.estado() != null) {
                carrito.actualizarEstado(EstadoCarrito.valueOf(cmd.estado()));
            }
            return toResult(carritoRepository.guardar(carrito));
        } else if ("ESTACIONAMIENTO".equalsIgnoreCase(cmd.tipo())) {
            var estacionamiento = estacionamientoRepository.buscarPorId(id)
                .orElseThrow(EstacionamientoException::noEncontrado);
            if (!estacionamiento.getIdCondominio().equals(condominioId)) {
                throw CondominioException.noEncontrado();
            }
            if (cmd.tipoVehiculo() != null && cmd.capacidadMaxima() != null) {
                estacionamiento.configurar(
                    TipoVehiculo.valueOf(cmd.tipoVehiculo()), cmd.capacidadMaxima());
            } else if (cmd.tipoVehiculo() == null && cmd.capacidadMaxima() == null) {
                estacionamiento.reiniciar();
            }
            return toResult(estacionamientoRepository.guardar(estacionamiento));
        }
        throw new IllegalArgumentException("Tipo de activo no válido: " + cmd.tipo());
    }

    private AdminAssetResult toResult(CarritoModel m) {
        return new AdminAssetResult(
            m.getId(), "CARRITO", m.getCodigo(), m.getEstado().name(),
            null, null, null, null, null, null, m.getIdCondominio()
        );
    }

    private AdminAssetResult toResult(EstacionamientoModel m) {
        return new AdminAssetResult(
            m.getId(), "ESTACIONAMIENTO", null, null,
            m.getNumero(),
            m.getTipoVehiculo() != null ? m.getTipoVehiculo().name() : null,
            m.getCapacidadMaxima(), m.getCantidadActual(),
            m.getDisponible(), m.getIdApartamento(),
            m.getIdCondominio()
        );
    }
}
