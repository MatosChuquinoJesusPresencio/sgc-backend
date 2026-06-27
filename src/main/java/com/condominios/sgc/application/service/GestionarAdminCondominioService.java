package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.command.ActualizarMiCondominioCommand;
import com.condominios.sgc.application.dto.result.AdminCondominioInfoResult;
import com.condominios.sgc.application.dto.result.AdminConfiguracionResult;
import com.condominios.sgc.application.port.in.GestionarAdminCondominioUseCase;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminCondominioService implements GestionarAdminCondominioUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final PaisRepositoryPort paisRepository;
    private final CiudadRepositoryPort ciudadRepository;

    public GestionarAdminCondominioService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.paisRepository = paisRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Override
    public AdminCondominioInfoResult obtenerMiCondominio() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        return toResult(condominio);
    }

    @Override
    @Transactional
    public AdminCondominioInfoResult actualizarMiCondominio(ActualizarMiCondominioCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        condominio.actualizar(cmd.nombre(), condominio.getIdPais(),
            condominio.getIdCiudad(), cmd.direccion());
        return toResult(condominioRepository.guardar(condominio));
    }

    @Override
    public AdminConfiguracionResult obtenerConfiguracion() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        return toConfigResult(condominio.getConfiguracion());
    }

    @Override
    @Transactional
    public AdminCondominioInfoResult actualizarConfiguracion(ActualizarConfiguracionCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        condominio.getConfiguracion().actualizar(
            cmd.maxAutos(), cmd.maxMotos(), cmd.penalizacionPorMin(),
            cmd.maxTiempoPrestamoMin(), cmd.maxEstacionamientos(),
            cmd.maxCarritos(), cmd.maxVehiculos(), cmd.maxInquilinos()
        );
        return toResult(condominioRepository.guardar(condominio));
    }

    private AdminCondominioInfoResult toResult(com.condominios.sgc.domain.model.CondominioModel c) {
        var nombrePais = c.getIdPais() != null
            ? paisRepository.buscarPorId(c.getIdPais()).map(p -> p.nombre()).orElse(null)
            : null;
        var nombreCiudad = c.getIdCiudad() != null
            ? ciudadRepository.buscarPorId(c.getIdCiudad()).map(ci -> ci.nombre()).orElse(null)
            : null;
        return new AdminCondominioInfoResult(
            c.getId(), c.getNombre(), c.getIdPais(), nombrePais,
            c.getIdCiudad(), nombreCiudad, c.getDireccion(),
            c.getActivo(), toConfigResult(c.getConfiguracion())
        );
    }

    private AdminConfiguracionResult toConfigResult(com.condominios.sgc.domain.model.ConfiguracionModel config) {
        return new AdminConfiguracionResult(
            config.getMaxAutos(), config.getMaxMotos(),
            config.getPenalizacionPorMin(), config.getMaxTiempoPrestamoMin(),
            config.getMaxEstacionamientosPorApartamento(),
            config.getMaxCarritosPorApartamento(),
            config.getMaxVehiculosPorPropietario(),
            config.getMaxInquilinosPorApartamento()
        );
    }
}
