package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.CatalogoUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminActivosUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminApartamentosUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminCondominioUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminEstructuraUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminLogsUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminUsuariosUseCase;
import com.condominios.sgc.application.port.in.GestionarCondominioUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioApartamentoUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioInquilinosUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioLogsUseCase;
import com.condominios.sgc.application.port.in.GestionarPropietarioVehiculosUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadAccesoUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadEstacionamientosUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadPrestamosUseCase;
import com.condominios.sgc.application.port.in.GestionarSeguridadVehiculosUseCase;
import com.condominios.sgc.application.port.in.GestionarDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarUsuariosGlobalUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.application.port.out.ConfiguracionRepositoryPort;
import com.condominios.sgc.application.port.out.EstacionamientoRepositoryPort;
import com.condominios.sgc.application.port.out.LogAccesoVehicularRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.application.service.AutenticacionService;
import com.condominios.sgc.application.service.CatalogoService;
import com.condominios.sgc.application.service.GestionarAdminActivosService;
import com.condominios.sgc.application.service.GestionarAdminApartamentosService;
import com.condominios.sgc.application.service.GestionarAdminCondominioService;
import com.condominios.sgc.application.service.GestionarAdminDashboardService;
import com.condominios.sgc.application.service.GestionarAdminEstructuraService;
import com.condominios.sgc.application.service.GestionarAdminLogsService;
import com.condominios.sgc.application.service.GestionarAdminUsuariosService;
import com.condominios.sgc.application.service.GestionarAdministradorService;
import com.condominios.sgc.application.service.GestionarCondominioService;
import com.condominios.sgc.application.service.GestionarDashboardService;
import com.condominios.sgc.application.service.GestionarPropietarioApartamentoService;
import com.condominios.sgc.application.service.GestionarPropietarioDashboardService;
import com.condominios.sgc.application.service.GestionarPropietarioInquilinosService;
import com.condominios.sgc.application.service.GestionarPropietarioLogsService;
import com.condominios.sgc.application.service.GestionarPropietarioVehiculosService;
import com.condominios.sgc.application.service.GestionarSeguridadAccesoService;
import com.condominios.sgc.application.service.GestionarSeguridadDashboardService;
import com.condominios.sgc.application.service.GestionarSeguridadEstacionamientosService;
import com.condominios.sgc.application.service.GestionarSeguridadPrestamosService;
import com.condominios.sgc.application.service.GestionarSeguridadVehiculosService;
import com.condominios.sgc.application.service.GestionarUsuariosGlobalService;
import com.condominios.sgc.application.service.PerfilService;

@Configuration
public class AutenticacionConfig {

    @Bean
    public AutenticacionService autenticacionService(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        return new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
    }

    @Bean
    public PerfilService perfilService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository) {
        return new PerfilService(securityService, usuarioRepository);
    }

    @Bean
    public CondominioIdResolver condominioIdResolver(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository) {
        return new CondominioIdResolver(securityService, usuarioRepository);
    }

    @Bean
    public GestionarAdministradorService gestionarAdministradorService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository,
            HashServicePort hashService,
            CorreoServicePort correoService) {
        return new GestionarAdministradorService(
            securityService, usuarioRepository, condominioRepository, paisRepository, 
            ciudadRepository, hashService, correoService);
    }

    @Bean
    public GestionarCondominioUseCase gestionarCondominioUseCase(
            CondominioRepositoryPort condominioRepository,
            UsuarioRepositoryPort usuarioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository) {
        return new GestionarCondominioService(
            condominioRepository, usuarioRepository, paisRepository, ciudadRepository);
    }

    @Bean
    public CatalogoUseCase catalogoUseCase(
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository) {
        return new CatalogoService(paisRepository, ciudadRepository);
    }

    @Bean
    public GestionarDashboardUseCase gestionarDashboardUseCase(
            CondominioRepositoryPort condominioRepository,
            UsuarioRepositoryPort usuarioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository) {
        return new GestionarDashboardService(condominioRepository, usuarioRepository, paisRepository, ciudadRepository);
    }

    @Bean
    public GestionarUsuariosGlobalUseCase gestionarUsuariosGlobalUseCase(
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            TokenRepositoryPort tokenRepository,
            HashServicePort hashService) {
        return new GestionarUsuariosGlobalService(
            usuarioRepository, condominioRepository, tokenRepository, hashService);
    }

    @Bean
    public GestionarAdminApartamentosUseCase gestionarAdminApartamentosUseCase(
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository,
            UsuarioRepositoryPort usuarioRepository,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminApartamentosService(
            apartamentoRepository, inquilinoRepository, vehiculoRepository,
            usuarioRepository, condominioIdResolver);
    }

    @Bean
    public GestionarAdminUsuariosUseCase gestionarAdminUsuariosUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminUsuariosService(usuarioRepository, hashService, condominioIdResolver);
    }

    @Bean
    public GestionarAdminEstructuraUseCase gestionarAdminEstructuraUseCase(
            CondominioRepositoryPort condominioRepository,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminEstructuraService(
            condominioRepository, condominioIdResolver);
    }

    @Bean
    public GestionarAdminDashboardUseCase gestionarAdminDashboardUseCase(
            CondominioRepositoryPort condominioRepository,
            UsuarioRepositoryPort usuarioRepository,
            VehiculoRepositoryPort vehiculoRepository,
            CarritoRepositoryPort carritoRepository,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminDashboardService(
            condominioRepository, usuarioRepository, vehiculoRepository, carritoRepository, condominioIdResolver);
    }

    @Bean
    public GestionarAdminCondominioUseCase gestionarAdminCondominioUseCase(
            CondominioRepositoryPort condominioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminCondominioService(
            condominioRepository, paisRepository, ciudadRepository, condominioIdResolver);
    }

    @Bean
    public GestionarAdminActivosUseCase gestionarAdminActivosUseCase(
            CarritoRepositoryPort carritoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            ConfiguracionRepositoryPort configuracionRepository,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminActivosService(
            carritoRepository, estacionamientoRepository, apartamentoRepository,
            configuracionRepository, condominioIdResolver);
    }

    @Bean
    public GestionarAdminLogsUseCase gestionarAdminLogsUseCase(
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            LogPrestamoCarritoRepositoryPort logCarritoRepository,
            CondominioIdResolver condominioIdResolver) {
        return new GestionarAdminLogsService(
            logAccesoRepository, logCarritoRepository, condominioIdResolver);
    }

    @Bean
    public GestionarPropietarioDashboardUseCase gestionarPropietarioDashboardUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            CondominioRepositoryPort condominioRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        return new GestionarPropietarioDashboardService(
            securityService, usuarioRepository, apartamentoRepository,
            condominioRepository, inquilinoRepository, vehiculoRepository);
    }

    @Bean
    public GestionarPropietarioApartamentoUseCase gestionarPropietarioApartamentoUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            CondominioRepositoryPort condominioRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        return new GestionarPropietarioApartamentoService(
            securityService, usuarioRepository, apartamentoRepository,
            condominioRepository, inquilinoRepository, vehiculoRepository);
    }

    @Bean
    public GestionarPropietarioInquilinosUseCase gestionarPropietarioInquilinosUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        return new GestionarPropietarioInquilinosService(
            securityService, usuarioRepository, apartamentoRepository, inquilinoRepository, vehiculoRepository);
    }

    @Bean
    public GestionarPropietarioVehiculosUseCase gestionarPropietarioVehiculosUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        return new GestionarPropietarioVehiculosService(
            securityService, usuarioRepository, vehiculoRepository);
    }

    @Bean
    public GestionarPropietarioLogsUseCase gestionarPropietarioLogsUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            LogPrestamoCarritoRepositoryPort logCarritoRepository) {
        return new GestionarPropietarioLogsService(
            securityService, usuarioRepository, logAccesoRepository, logCarritoRepository);
    }

    @Bean
    public GestionarSeguridadDashboardUseCase gestionarSeguridadDashboardUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogPrestamoCarritoRepositoryPort logPrestamoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository) {
        return new GestionarSeguridadDashboardService(
            securityService, usuarioRepository, estacionamientoRepository,
            logPrestamoRepository, logAccesoRepository);
    }

    @Bean
    public GestionarSeguridadEstacionamientosUseCase gestionarSeguridadEstacionamientosUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        return new GestionarSeguridadEstacionamientosService(
            securityService, usuarioRepository, estacionamientoRepository);
    }

    @Bean
    public GestionarSeguridadVehiculosUseCase gestionarSeguridadVehiculosUseCase(
            VehiculoRepositoryPort vehiculoRepository,
            UsuarioRepositoryPort usuarioRepository) {
        return new GestionarSeguridadVehiculosService(
            vehiculoRepository, usuarioRepository);
    }

    @Bean
    public GestionarSeguridadPrestamosUseCase gestionarSeguridadPrestamosUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CarritoRepositoryPort carritoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            LogPrestamoCarritoRepositoryPort logPrestamoRepository) {
        return new GestionarSeguridadPrestamosService(
            securityService, usuarioRepository, carritoRepository,
            apartamentoRepository, logPrestamoRepository);
    }

    @Bean
    public GestionarSeguridadAccesoUseCase gestionarSeguridadAccesoUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            VehiculoRepositoryPort vehiculoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository) {
        return new GestionarSeguridadAccesoService(
            securityService, usuarioRepository, vehiculoRepository,
            estacionamientoRepository, logAccesoRepository);
    }
}
