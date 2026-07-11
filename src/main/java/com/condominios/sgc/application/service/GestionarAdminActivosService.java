package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarStatusAssetCommand;
import com.condominios.sgc.application.dto.command.AsignarParkingCommand;
import com.condominios.sgc.application.dto.command.CrearAssetCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminAssetResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminActivosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.ConfiguracionRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.CarritoException;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.EstacionamientoException;
import com.condominios.sgc.domain.shared.exception.ParametroInvalidoException;
import com.condominios.sgc.domain.shared.exception.VehiculoException;
import com.condominios.sgc.domain.type.EstadoCarrito;
import com.condominios.sgc.domain.type.TipoVehiculo;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminActivosService implements GestionarAdminActivosUseCase {

    private final CarritoRepositoryPort carritoRepository;
    private final EstacionamientoRepositoryPort estacionamientoRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final ConfiguracionRepositoryPort configuracionRepository;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminActivosService(
            CarritoRepositoryPort carritoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            ConfiguracionRepositoryPort configuracionRepository,
            VehiculoRepositoryPort vehiculoRepository,
            CondominioIdResolver condominioIdResolver) {
        this.carritoRepository = carritoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.configuracionRepository = configuracionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public PaginaResult<AdminAssetResult> listar(Long condominioIdOverride, String type, PaginaQuery pagina) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

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
    public AdminAssetResult crear(Long condominioIdOverride, CrearAssetCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

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
    public AdminAssetResult actualizarStatus(Long condominioIdOverride, Long id, ActualizarStatusAssetCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

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
    public AdminAssetResult asignarApartamento(Long condominioIdOverride, Long id, AsignarParkingCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

        var estacionamiento = estacionamientoRepository.buscarPorId(id)
            .orElseThrow(EstacionamientoException::noEncontrado);
        if (!condominioId.equals(estacionamiento.getIdCondominio())) {
            throw CondominioException.noEncontrado();
        }

        if (estacionamiento.getIdApartamento() != null
                && estacionamiento.getCantidadActual() > 0
                && (cmd.idApartamento() == null
                    || !cmd.idApartamento().equals(estacionamiento.getIdApartamento()))) {
            throw EstacionamientoException.ocupado();
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

    @Override
    @Transactional
    public void eliminar(Long condominioIdOverride, Long id, String type) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

        if ("CARRITO".equalsIgnoreCase(type)) {
            var carrito = carritoRepository.buscarPorId(id)
                .orElseThrow(CarritoException::noEncontrado);
            if (!carrito.getIdCondominio().equals(condominioId)) {
                throw CondominioException.noEncontrado();
            }
            if (carrito.getEstado() == EstadoCarrito.EN_USO) {
                throw CarritoException.enUso();
            }
            carritoRepository.eliminarPorId(id);
        } else if ("ESTACIONAMIENTO".equalsIgnoreCase(type)) {
            var estacionamiento = estacionamientoRepository.buscarPorId(id)
                .orElseThrow(EstacionamientoException::noEncontrado);
            if (!estacionamiento.getIdCondominio().equals(condominioId)) {
                throw CondominioException.noEncontrado();
            }
            if (estacionamiento.getCantidadActual() > 0) {
                throw EstacionamientoException.ocupado();
            }
            estacionamientoRepository.eliminarPorId(id);
        } else {
            throw new ParametroInvalidoException("Tipo de activo no válido: " + type);
        }
    }

    @Override
    @Transactional
    public void desasignarVehiculo(Long condominioIdOverride, Long vehiculoId) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var vehiculo = vehiculoRepository.buscarPorId(vehiculoId)
            .orElseThrow(VehiculoException::noEncontrado);
        if (!condominioId.equals(vehiculo.getIdCondominio())) {
            throw VehiculoException.noEncontrado();
        }
        if (vehiculo.getIdEstacionamiento() != null) {
            var estacionamiento = estacionamientoRepository.buscarPorId(vehiculo.getIdEstacionamiento())
                .orElse(null);
            if (estacionamiento != null) {
                estacionamiento.decrementarOcupacion();
                estacionamientoRepository.guardar(estacionamiento);
            }
        }
        vehiculo.desasignarEstacionamiento();
        vehiculoRepository.guardar(vehiculo);
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
