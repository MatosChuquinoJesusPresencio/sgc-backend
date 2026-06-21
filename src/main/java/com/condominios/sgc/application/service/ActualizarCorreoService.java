package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.ActualizarCorreoUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.JwtServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.TipoToken;

public class ActualizarCorreoService implements ActualizarCorreoUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final JwtServicePort jwtService;
    private final TokenRepositoryPort tokenRepository;
    private final CorreoServicePort correoService;

    public ActualizarCorreoService(SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            JwtServicePort jwtService,
            TokenRepositoryPort tokenRepository,
            CorreoServicePort correoService) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.correoService = correoService;
    }

    @Override
    public void ejecutar(String nuevoCorreo, String contrasena) {
        var idUsuario = securityService.obtenerIdUsuario();
        var usuario = usuarioRepository.buscarPorId(idUsuario)
            .orElseThrow(UsuarioException::noEncontrado);
            
        usuarioRepository.buscarPorCorreo(nuevoCorreo)
            .ifPresent(u -> { throw UsuarioException.correoYaRegistrado(); });

        usuario.cambiarCorreo(nuevoCorreo);

        var valorToken = jwtService.generarToken(idUsuario, TipoToken.VERIFICACION);
        tokenRepository.guardar(new TokenModel(TipoToken.VERIFICACION, valorToken,
            jwtService.obtenerExpiracion(TipoToken.VERIFICACION), idUsuario));

        correoService.enviarVerificacion(nuevoCorreo,
            usuario.getNombreCompleto().formatoCompleto(), valorToken);

        usuarioRepository.guardar(usuario);
    }
}
