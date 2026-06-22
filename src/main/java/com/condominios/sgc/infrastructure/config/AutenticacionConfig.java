package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.port.in.ActualizarCorreoUseCase;
import com.condominios.sgc.application.port.in.ActualizarPerfilUseCase;
import com.condominios.sgc.application.port.in.CambiarContrasenaUseCase;
import com.condominios.sgc.application.port.in.CatalogoUseCase;
import com.condominios.sgc.application.port.in.CerrarSesionUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminActivosUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminApartamentosUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminCondominioUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminEstructuraUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminLogsUseCase;
import com.condominios.sgc.application.port.in.GestionarAdminUsuariosUseCase;
import com.condominios.sgc.application.port.in.GestionarCondominioUseCase;
import com.condominios.sgc.application.port.in.GestionarDashboardUseCase;
import com.condominios.sgc.application.port.in.GestionarUsuariosGlobalUseCase;
import com.condominios.sgc.application.port.in.IniciarSesionUseCase;
import com.condominios.sgc.application.port.in.ObtenerPerfilUseCase;
import com.condominios.sgc.application.port.in.ObtenerUsuarioActualUseCase;
import com.condominios.sgc.application.port.in.OlvidasteContrasenaUseCase;
import com.condominios.sgc.application.port.in.RefrescarTokenUseCase;
import com.condominios.sgc.application.port.in.RestablecerContrasenaUseCase;
import com.condominios.sgc.application.port.in.VerificarEmailUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
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
import com.condominios.sgc.application.service.GestionarUsuariosGlobalService;
import com.condominios.sgc.application.service.PerfilService;

@Configuration
public class AutenticacionConfig {

    @Bean
    public IniciarSesionUseCase iniciarSesionUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::iniciarSesion;
    }

    @Bean
    public RefrescarTokenUseCase refrescarTokenUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::refrescarToken;
    }

    @Bean
    public CerrarSesionUseCase cerrarSesionUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::cerrarSesion;
    }

    @Bean
    public OlvidasteContrasenaUseCase olvidasteContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::olvidasteContrasena;
    }

    @Bean
    public RestablecerContrasenaUseCase restablecerContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::restablecerContrasena;
    }

    @Bean
    public ObtenerUsuarioActualUseCase obtenerUsuarioActualUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::obtenerUsuarioActual;
    }

    @Bean
    public CambiarContrasenaUseCase cambiarContrasenaUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::cambiarContrasena;
    }

    @Bean
    public ActualizarCorreoUseCase actualizarCorreoUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::actualizarCorreo;
    }

    @Bean
    public VerificarEmailUseCase verificarEmailUseCase(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            SecurityServicePort securityService,
            CorreoServicePort correoService) {
        var svc = new AutenticacionService(usuarioRepository, hashService, jwtService,
            tokenRepository, securityService, correoService);
        return svc::verificarEmail;
    }

    @Bean
    public ObtenerPerfilUseCase obtenerPerfilUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository) {
        var svc = new PerfilService(securityService, usuarioRepository);
        return svc::obtenerPerfil;
    }

    @Bean
    public ActualizarPerfilUseCase actualizarPerfilUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository) {
        var svc = new PerfilService(securityService, usuarioRepository);
        return svc::actualizarPerfil;
    }

    @Bean
    public GestionarAdministradorService gestionarAdministradorService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            HashServicePort hashService,
            CorreoServicePort correoService) {
        return new GestionarAdministradorService(
            securityService, usuarioRepository, condominioRepository,
            hashService, correoService);
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
            UsuarioRepositoryPort usuarioRepository) {
        return new GestionarDashboardService(condominioRepository, usuarioRepository);
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
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository) {
        return new GestionarAdminApartamentosService(
            securityService, usuarioRepository, condominioRepository,
            apartamentoRepository, inquilinoRepository);
    }

    @Bean
    public GestionarAdminUsuariosUseCase gestionarAdminUsuariosUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService) {
        return new GestionarAdminUsuariosService(securityService, usuarioRepository, hashService);
    }

    @Bean
    public GestionarAdminEstructuraUseCase gestionarAdminEstructuraUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository) {
        return new GestionarAdminEstructuraService(
            securityService, usuarioRepository, condominioRepository);
    }

    @Bean
    public GestionarAdminDashboardUseCase gestionarAdminDashboardUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            VehiculoRepositoryPort vehiculoRepository,
            CarritoRepositoryPort carritoRepository) {
        return new GestionarAdminDashboardService(
            securityService, usuarioRepository, condominioRepository,
            vehiculoRepository, carritoRepository);
    }

    @Bean
    public GestionarAdminCondominioUseCase gestionarAdminCondominioUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository) {
        return new GestionarAdminCondominioService(
            securityService, usuarioRepository, condominioRepository,
            paisRepository, ciudadRepository);
    }

    @Bean
    public GestionarAdminActivosUseCase gestionarAdminActivosUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CarritoRepositoryPort carritoRepository,
            EstacionamientoRepositoryPort estacionamientoRepository) {
        return new GestionarAdminActivosService(
            securityService, usuarioRepository, carritoRepository, estacionamientoRepository);
    }

    @Bean
    public GestionarAdminLogsUseCase gestionarAdminLogsUseCase(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            LogAccesoVehicularRepositoryPort logAccesoRepository,
            LogPrestamoCarritoRepositoryPort logCarritoRepository) {
        return new GestionarAdminLogsService(
            securityService, usuarioRepository, logAccesoRepository, logCarritoRepository);
    }
}
