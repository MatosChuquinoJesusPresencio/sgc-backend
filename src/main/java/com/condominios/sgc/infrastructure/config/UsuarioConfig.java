package com.condominios.sgc.infrastructure.config;

import com.condominios.sgc.application.impl.ActualizarEstadoUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.ActualizarUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.EliminarUsuarioUseCaseImpl;
import com.condominios.sgc.application.impl.ListarUsuariosUseCaseImpl;
import com.condominios.sgc.application.impl.ObtenerUsuarioUseCaseImpl;
import com.condominios.sgc.application.usecase.ActualizarEstadoUsuarioUseCase;
import com.condominios.sgc.application.usecase.ActualizarUsuarioUseCase;
import com.condominios.sgc.application.usecase.EliminarUsuarioUseCase;
import com.condominios.sgc.application.usecase.ListarUsuariosUseCase;
import com.condominios.sgc.application.usecase.ObtenerUsuarioUseCase;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.adapter.UsuarioAdapter;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioConfig {

    @Bean
    public UsuarioPort usuarioPort(
            UsuarioRepository usuarioRepository,
            ApartamentoRepository apartamentoRepository,
            VehiculoRepository vehiculoRepository,
            CondominioRepository condominioRepository) {
        return new UsuarioAdapter(usuarioRepository, apartamentoRepository, vehiculoRepository, condominioRepository);
    }

    @Bean
    public ObtenerUsuarioUseCase obtenerUsuarioUseCase(UsuarioPort usuarioPort) {
        return new ObtenerUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public ActualizarUsuarioUseCase actualizarUsuarioUseCase(UsuarioPort usuarioPort) {
        return new ActualizarUsuarioUseCaseImpl(usuarioPort);
    }

    @Bean
    public EliminarUsuarioUseCase eliminarUsuarioUseCase(
            UsuarioPort usuarioPort,
            AutenticacionPort autenticacionPort) {
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
}
