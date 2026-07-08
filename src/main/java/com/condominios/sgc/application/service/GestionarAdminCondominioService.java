package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarConfiguracionCommand;
import com.condominios.sgc.application.dto.command.ActualizarMiCondominioCommand;
import com.condominios.sgc.application.dto.result.AdminCondominioInfoResult;
import com.condominios.sgc.application.dto.result.AdminConfiguracionResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminCondominioUseCase;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.domain.shared.exception.CondominioException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminCondominioService implements GestionarAdminCondominioUseCase {

    private final CondominioRepositoryPort condominioRepository;
    private final PaisRepositoryPort paisRepository;
    private final CiudadRepositoryPort ciudadRepository;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminCondominioService(
            CondominioRepositoryPort condominioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository,
            CondominioIdResolver condominioIdResolver) {
        this.condominioRepository = condominioRepository;
        this.paisRepository = paisRepository;
        this.ciudadRepository = ciudadRepository;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public AdminCondominioInfoResult obtenerMiCondominio(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        return toResult(condominio);
    }

    @Override
    @Transactional
    public AdminCondominioInfoResult actualizarMiCondominio(Long condominioIdOverride, ActualizarMiCondominioCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        condominio.actualizar(cmd.nombre(), condominio.getIdPais(),
            condominio.getIdCiudad(), cmd.direccion());
        return toResult(condominioRepository.guardar(condominio));
    }

    @Override
    public AdminConfiguracionResult obtenerConfiguracion(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        return toConfigResult(condominio.getConfiguracion());
    }

    @Override
    @Transactional
    public AdminCondominioInfoResult actualizarConfiguracion(Long condominioIdOverride, ActualizarConfiguracionCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var condominio = condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
        condominio.getConfiguracion().actualizar(
            cmd.maxAutos(), cmd.maxMotos(), cmd.penalizacionPorMin(),
            cmd.maxTiempoPrestamoMin(), cmd.maxEstacionamientosPorDepto(),
            cmd.maxCarritosPorDepto(), cmd.maxVehiculosPorDepto(), cmd.maxInquilinosPorDepto()
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
