package com.condominios.sgc.application.helper;

import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.Rol;

public class CondominioIdResolver {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;

    public CondominioIdResolver(SecurityServicePort securityService, UsuarioRepositoryPort usuarioRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
    }

    public Long resolver(Long condominioIdOverride) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        if (usuario.getRol() == Rol.SUPER_ADMINISTRADOR) {
            if (condominioIdOverride == null) {
                throw CondominioException.idCondominioInvalido();
            }
            return condominioIdOverride;
        }
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        return condominioId;
    }
}
