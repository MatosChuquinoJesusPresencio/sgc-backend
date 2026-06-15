package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.usuario.*;
import com.condominios.sgc.application.usecase.usuario.*;
import com.condominios.sgc.domain.port.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public ActualizarUsuarioPorIdUseCase actualizarUsuarioPorIdUseCase(UsuarioPort usuarioPort) {
        return new ActualizarUsuarioPorIdUseCaseImpl(usuarioPort);
    }

    @Bean
    public ActualizarMiUsuarioUseCase actualizarMiUsuarioUseCase(UsuarioPort usuarioPort) {
        return new ActualizarMiUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public CambiarEstadoActivoUsuarioUseCase cambiarEstadoActivoUsuarioUseCase(UsuarioPort usuarioPort) {
        return new CambiarEstadoActivoUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort, CorreoPort correoPort) {
        return new CrearUsuarioUseCaseImpl(usuarioPort, autenticacionPort, correoPort);
    }

    @Bean
    public EliminarUsuarioPorIdUseCase eliminarUsuarioPorIdUseCase(UsuarioPort usuarioPort) {
        return new EliminarUsuarioPorIdUseCaseImpl(usuarioPort);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioPort usuarioPort) {
        return new ListarUsuariosUseCaseImpl(usuarioPort);
    }

    @Bean
    public ObtenerUsuarioPorCorreoUseCase obtenerUsuarioPorCorreoUseCase(UsuarioPort usuarioPort) {
        return new ObtenerUsuarioPorCorreoUseCaseImpl(usuarioPort);
    }

    @Bean
    public ObtenerUsuarioPorIdUseCase obtenerUsuarioPorIdUseCase(UsuarioPort usuarioPort) {
        return new ObtenerUsuarioPorIdUseCaseImpl(usuarioPort);
    }

    @Bean
    public SolicitarCambioCorreoUseCase solicitarCambioCorreoUseCase(UsuarioPort usuarioPort, TokenPort tokenPort, CorreoPort correoPort) {
        return new SolicitarCambioCorreoUseCaseImpl(usuarioPort, tokenPort, correoPort);
    }

}
