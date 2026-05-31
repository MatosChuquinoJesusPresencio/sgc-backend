package com.condominios.sgc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.condominios.sgc.application.impl.ActualizarCorreoUseCaseImpl;
import com.condominios.sgc.application.impl.ActualizarEstadoUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.ActualizarUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.CambiarContrasenaUseCaseImpl;
import com.condominios.sgc.application.impl.CrearInquilinoUseCaseImpl;
import com.condominios.sgc.application.impl.CrearUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.ListarUsuariosUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.VerificarCorreoUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarCorreoUseCase;
import com.condominios.sgc.application.usecase.ActualizarEstadoUsuarioUseCase;
import com.condominios.sgc.application.usecase.ActualizarUsuarioUseCase;
import com.condominios.sgc.application.usecase.CambiarContrasenaUseCase;
import com.condominios.sgc.application.usecase.CrearInquilinoUseCase;
import com.condominios.sgc.application.usecase.CrearUsuarioUseCase;
import com.condominios.sgc.application.usecase.EliminarUsuarioUseCase;
import com.condominios.sgc.application.usecase.ListarUsuariosUseCase;
import com.condominios.sgc.application.usecase.ObtenerUsuarioUseCase;
import com.condominios.sgc.application.usecase.VerificarCorreoUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.ConfiguracionPort;
import com.condominios.sgc.domain.port.InquilinoPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.domain.port.VerificacionTokenPort;

@Configuration
public class UsuarioConfig {

    @Bean
    public CrearUsuarioUseCase crearUsuarioUseCase(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort) {
        return new CrearUsuarioUseCaseImpl(autenticacionPort, usuarioPort);
    }

    @Bean
    public ActualizarUsuarioUseCase actualizarUsuarioUseCase(UsuarioPort usuarioPort) {
        return new ActualizarUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public ObtenerUsuarioUseCase obtenerUsuarioUseCase(UsuarioPort usuarioPort) {
        return new ObtenerUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public EliminarUsuarioUseCase eliminarUsuarioUseCase(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort) {
        return new EliminarUsuarioUseCaseImpl(usuarioPort, autenticacionPort);
    }

    @Bean
    public ListarUsuariosUseCase listarUsuariosUseCase(UsuarioPort usuarioPort) {
        return new ListarUsuariosUseCaseImpl(usuarioPort);
    }

    @Bean
    public ActualizarEstadoUsuarioUseCase actualizarEstadoUsuarioUseCase(UsuarioPort usuarioPort) {
        return new ActualizarEstadoUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public CambiarContrasenaUseCase cambiarContrasenaUseCase(AutenticacionPort autenticacionPort, UsuarioPort usuarioPort) {
        return new CambiarContrasenaUseCaseImpl(autenticacionPort, usuarioPort);
    }

    @Bean
    public ActualizarCorreoUseCase actualizarCorreoUseCase(UsuarioPort usuarioPort, VerificacionTokenPort verificacionTokenPort) {
        return new ActualizarCorreoUseCaseImpl(usuarioPort, verificacionTokenPort);
    }

    @Bean
    public VerificarCorreoUseCase verificarCorreoUseCase(UsuarioPort usuarioPort, AutenticacionPort autenticacionPort, VerificacionTokenPort verificacionTokenPort) {
        return new VerificarCorreoUseCaseImpl(usuarioPort, autenticacionPort, verificacionTokenPort);
    }

    @Bean
    public CrearInquilinoUseCase crearInquilinoUseCase(InquilinoPort inquilinoPort, ConfiguracionPort configuracionPort) {
        return new CrearInquilinoUseCaseImpl(inquilinoPort, configuracionPort);
    }
}
