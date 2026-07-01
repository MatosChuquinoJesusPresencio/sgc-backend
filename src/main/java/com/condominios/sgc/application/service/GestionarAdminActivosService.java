package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.AsignarParkingCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminAssetResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarAdminActivosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.ConfiguracionRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.CarritoException;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.EstacionamientoException;
import com.condominios.sgc.domain.shared.exception.ParametroInvalidoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.EstadoCarrito;
import com.condominios.sgc.domain.type.TipoVehiculo;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminActivosService implements GestionarAdminActivosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CarritoRepositoryPort carritoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final ConfiguracionRepositoryPort configuracionRepository;

    public GestionarAdminActivosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CarritoRepositoryPort carritoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            ConfiguracionRepositoryPort configuracionRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.configuracionRepository = configuracionRepository;
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
    public PaginaResult<AdminAssetResult> listar(String type, PaginaQuery pagina) {
        var condominioId = obtenerCondominioId();

        if ("CARRITO".equalsIgnoreCase(type)) {
            var result = carritoRepository.buscarPorCondominio(condominioId, pagina);
            var items = result.items().stream().map(this::toResult).toList();
            return new PaginaResult<>(items, result.total(), result.pagina(), result.tamano());
        }

        if ("ESTACIONAMIENTO".equalsIgnoreCase(type)) {
            var result = estacionamientoRepository.buscarPorCondominio(condominioId, pagina);
            var items = result.items().stream().map(this::toResult).toList();
            return new PaginaResult<>(items, result.total(), result.pagina(), result.tamano());
        }

        return new PaginaResult<>(List.of(), 0, pagina.pagina(), pagina.tamano());
    }

    @Override
    @Transactional
    public AdminAssetResult crear(CrearAssetCommand cmd) {
        var condominioId = obtenerCondominioId();

        if ("CARRITO".equalsIgnoreCase(cmd.tipo())) {
            var modelo = new CarritoModel(cmd.codigo(), condominioId);
            return toResult(carritoRepository.guardar(modelo));
        } else if ("ESTACIONAMIENTO".equalsIgnoreCase(cmd.tipo())) {
            var modelo = new EstacionamientoModel(cmd.numero(), condominioId);
            return toResult(estacionamientoRepository.guardar(modelo));
        }
        throw new ParametroInvalidoException("Tipo de activo no válido: " + cmd.tipo());
    }

    @Override
    @Transactional
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
        throw new ParametroInvalidoException("Tipo de activo no válido: " + cmd.tipo());
    }

    @Override
    @Transactional
    public AdminAssetResult asignarApartamento(Long id, AsignarParkingCommand cmd) {
        var condominioId = obtenerCondominioId();

        var estacionamiento = estacionamientoRepository.buscarPorId(id)
            .orElseThrow(EstacionamientoException::noEncontrado);
        if (!condominioId.equals(estacionamiento.getIdCondominio())) {
            throw CondominioException.noEncontrado();
        }

        if (cmd.idApartamento() != null) {
            if (!apartamentoRepository.existePorIdYCondominio(cmd.idApartamento(), condominioId)) {
                throw ApartamentoException.noEncontrado();
            }
            var config = configuracionRepository.buscarPorCondominioId(condominioId)
                .orElseThrow(CondominioException::noEncontrado);
            var actuales = (int) estacionamientoRepository.contarPorApartamento(cmd.idApartamento());
            if (!config.puedeAsignarEstacionamiento(actuales)) {
                throw EstacionamientoException.limiteAlcanzado();
            }
            estacionamiento.asignarApartamento(cmd.idApartamento());
        } else {
            estacionamiento.desasignarApartamento();
        }

        return toResult(estacionamientoRepository.guardar(estacionamiento));
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
