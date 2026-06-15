package com.condominios.sgc.application.impl.autenticacion;

import com.condominios.sgc.application.dto.command.RefrescarTokenCommand;
import com.condominios.sgc.application.dto.response.IniciarSesionResponse;
import com.condominios.sgc.application.usecase.autenticacion.RefrescarTokenUseCase;
import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.AutenticacionPort;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;

public class RefrescarTokenUseCaseImpl implements RefrescarTokenUseCase {
    private final AutenticacionPort autenticacionPort;
    private final TokenPort tokenPort;
    private final UsuarioPort usuarioPort;

    public RefrescarTokenUseCaseImpl(AutenticacionPort autenticacionPort, TokenPort tokenPort, UsuarioPort usuarioPort) {
        this.autenticacionPort = autenticacionPort;
        this.tokenPort = tokenPort;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public IniciarSesionResponse ejecutar(RefrescarTokenCommand command) {
        Long idUsuario = autenticacionPort.validarToken(command.token())
            .orElseThrow(TokenException::noEncontrado);

        autenticacionPort.invalidarToken(command.token());

        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
            .orElseThrow(UsuarioException::noEncontrado);

        boolean recuerdame = autenticacionPort.esRecuerdame(command.token());

        TokenModel nuevoAccess = tokenPort.generarToken(TipoToken.ACCESS, usuario.getId(), recuerdame);
        TokenModel nuevoRefresh = tokenPort.generarToken(TipoToken.REFRESH, usuario.getId(), recuerdame);

        return new IniciarSesionResponse(nuevoAccess.getToken(), nuevoRefresh.getToken(),
                usuario.getId(), usuario.getRol(), usuario.getNombres(), recuerdame);
    }
}
