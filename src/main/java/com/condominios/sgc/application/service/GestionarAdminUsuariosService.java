package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarAdminUserCommand;
import com.condominios.sgc.application.dto.command.CrearAdminUserCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminUserResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarAdminUsuariosUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.Rol;
import org.springframework.transaction.annotation.Transactional;

public class GestionarAdminUsuariosService implements GestionarAdminUsuariosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final HashServicePort hashService;

    public GestionarAdminUsuariosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.hashService = hashService;
    }

    @Override
    public PaginaResult<AdminUserResult> listar(String search, String rol, Boolean activo, PaginaQuery query) {
        var condominioId = obtenerCondominioId();
        var usuarios = usuarioRepository.buscarPorCondominio(
            condominioId, search, rol, activo, query.pagina(), query.tamano());
        long total = usuarioRepository.contarPorCondominio(condominioId, search, rol, activo);
        var items = usuarios.stream().map(this::toResult).toList();
        return new PaginaResult<>(items, total, query.pagina(), query.tamano());
    }

    @Transactional
    @Override
    public AdminUserResult crear(CrearAdminUserCommand cmd) {
        var rolDestino = Rol.valueOf(cmd.rol());
        Rol.ADMINISTRADOR_CONDOMINIO.validarPuedeAsignarRol(rolDestino);

        if (usuarioRepository.existePorCorreo(cmd.correo())) {
            throw UsuarioException.correoYaRegistrado();
        }

        var condominioId = obtenerCondominioId();
        var usuario = new UsuarioModel(
            cmd.nombres(), cmd.apellidos(), cmd.correo(),
            cmd.telefono(), rolDestino, hashService.hashear(cmd.contrasena()));
        usuario.asignarCondominio(condominioId);
        return toResult(usuarioRepository.guardar(usuario));
    }

    @Transactional
    @Override
    public AdminUserResult actualizar(Long id, ActualizarAdminUserCommand cmd) {
        var condominioId = obtenerCondominioId();
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        if (!condominioId.equals(usuario.getIdCondominio())) {
            throw UsuarioException.noEncontrado();
        }
        usuario.actualizar(cmd.nombres(), cmd.apellidos(), cmd.telefono());
        return toResult(usuarioRepository.guardar(usuario));
    }

    private Long obtenerCondominioId() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        return condominioId;
    }

    private AdminUserResult toResult(UsuarioModel u) {
        return new AdminUserResult(
            u.getId(), u.getNombres(), u.getApellidos(),
            u.getCorreo().direccion(),
            u.getTelefono() != null ? u.getTelefono().numero() : null,
            u.getRol().name(), u.getActivo(), u.getFechaCreacion()
        );
    }
}
