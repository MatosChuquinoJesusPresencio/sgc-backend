package com.condominios.sgc.application.service;

import com.condominios.sgc.application.port.in.VerificarEmailUseCase;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.shared.exception.TokenException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.TipoToken;

public class VerificarEmailService implements VerificarEmailUseCase {

    private final TokenRepositoryPort tokenRepository;
    private final UsuarioRepositoryPort usuarioRepository;

    public VerificarEmailService(TokenRepositoryPort tokenRepository,
            UsuarioRepositoryPort usuarioRepository) {
        this.tokenRepository = tokenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void ejecutar(String token) {
        var tokenModel = tokenRepository.obtenerPorToken(token)
            .orElseThrow(TokenException::noEncontrado);

        if (tokenModel.getTipo() != TipoToken.VERIFICACION)
            throw TokenException.tokenExpirado();

        tokenModel.validarExpiracion();

        var usuario = usuarioRepository.buscarPorId(tokenModel.getIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);

        usuario.confirmarCorreo();
        usuarioRepository.guardar(usuario);
        tokenRepository.eliminar(tokenModel);
    }
}
