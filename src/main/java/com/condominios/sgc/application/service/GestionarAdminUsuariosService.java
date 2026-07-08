package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarAdminUserCommand;
import com.condominios.sgc.application.dto.command.CrearAdminUserCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminUserResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminUsuariosUseCase;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.Rol;

import org.springframework.transaction.annotation.Transactional;

public class GestionarAdminUsuariosService implements GestionarAdminUsuariosUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final HashServicePort hashService;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminUsuariosService(
            UsuarioRepositoryPort usuarioRepository,
            HashServicePort hashService,
            CondominioIdResolver condominioIdResolver) {
        this.usuarioRepository = usuarioRepository;
        this.hashService = hashService;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public PaginaResult<AdminUserResult> listar(Long condominioIdOverride, String search, String rol, Boolean activo, PaginaQuery query) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var pagina = usuarioRepository.buscarPorCondominio(condominioId, search, rol, activo, query);
        var items = pagina.items().stream().map(this::toResult).toList();
        return new PaginaResult<>(items, pagina.total(), pagina.pagina(), pagina.tamano());
    }

    @Override
    public AdminUserResult crear(Long condominioIdOverride, CrearAdminUserCommand cmd) {
        var rolDestino = Rol.valueOf(cmd.rol());
        Rol.ADMINISTRADOR_CONDOMINIO.validarPuedeAsignarRol(rolDestino);

        if (usuarioRepository.existePorCorreo(cmd.correo())) {
            throw UsuarioException.correoYaRegistrado();
        }

        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var usuario = new UsuarioModel(
            cmd.nombres(), cmd.apellidos(), cmd.correo(),
            cmd.telefono(), rolDestino, hashService.hashear(cmd.contrasena()));
        usuario.asignarCondominio(condominioId);
        return toResult(usuarioRepository.guardar(usuario));
    }

    @Override
    public AdminUserResult actualizar(Long condominioIdOverride, Long id, ActualizarAdminUserCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        if (!condominioId.equals(usuario.getIdCondominio())) {
            throw UsuarioException.noEncontrado();
        }
        usuario.actualizar(cmd.nombres(), cmd.apellidos(), cmd.telefono());
        if (cmd.rol() != null) {
            var rolDestino = Rol.valueOf(cmd.rol());
            Rol.ADMINISTRADOR_CONDOMINIO.validarPuedeAsignarRol(rolDestino);
            usuario.cambiarRol(rolDestino);
        }
        return toResult(usuarioRepository.guardar(usuario));
    }

    @Override
    @Transactional
    public void activarDesactivar(Long condominioIdOverride, Long id, Boolean activo) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        if (!condominioId.equals(usuario.getIdCondominio()))
            throw UsuarioException.noEncontrado();
        if (Boolean.TRUE.equals(activo)) {
            usuario.activar();
        } else {
            usuario.desactivar();
        }
        usuarioRepository.guardar(usuario);
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
