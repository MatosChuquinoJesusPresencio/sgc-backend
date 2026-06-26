package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ForzarCambioContrasenaCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.dto.result.UsuarioGlobalResult;
import com.condominios.sgc.application.port.in.GestionarUsuariosGlobalUseCase;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarUsuariosGlobalService implements GestionarUsuariosGlobalUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final TokenRepositoryPort tokenRepository;
    private final HashServicePort hashService;

    public GestionarUsuariosGlobalService(
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            TokenRepositoryPort tokenRepository,
            HashServicePort hashService) {
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.tokenRepository = tokenRepository;
        this.hashService = hashService;
    }

    @Override
    public PaginaResult<UsuarioGlobalResult> listar(String search, String rol, Boolean activo, PaginaQuery query) {
        var pagina = usuarioRepository.buscarTodos(search, rol, activo, query);
        var items = pagina.items().stream().map(u -> {
            String nombreCondominio = null;
            if (u.getIdCondominio() != null) {
                nombreCondominio = condominioRepository.buscarNombrePorId(u.getIdCondominio()).orElse(null);
            }
            return new UsuarioGlobalResult(
                u.getId(), u.getNombres(), u.getApellidos(),
                u.getCorreo().direccion(),
                u.getTelefono() != null ? u.getTelefono().numero() : null,
                u.getRol().name(), u.getActivo(),
                u.getCorreoVerificado(), u.getIdCondominio(),
                nombreCondominio, u.getFechaCreacion()
            );
        }).toList();
        return new PaginaResult<>(items, pagina.total(), pagina.pagina(), pagina.tamano());
    }

    @Override
    public void invalidarSesion(Long id) {
        if (usuarioRepository.buscarPorId(id).isEmpty())
            throw UsuarioException.noEncontrado();
        tokenRepository.eliminarPorUsuarioId(id);
    }

    @Override
    public void forzarCambioContrasena(Long id, ForzarCambioContrasenaCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.cambiarContrasena(hashService.hashear(cmd.nuevaContrasena()));
        usuarioRepository.guardar(usuario);
    }
}
