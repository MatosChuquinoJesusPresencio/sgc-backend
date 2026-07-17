package com.condominios.sgc.application.service;


import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarAdministradorCommand;
import com.condominios.sgc.application.dto.command.CrearAdministradorCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdministradorResult;
import com.condominios.sgc.application.dto.result.CondominioSimpleResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarAdministradorUseCase;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.CorreoServicePort;
import com.condominios.sgc.application.port.out.service.HashServicePort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.Rol;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdministradorService implements GestionarAdministradorUseCase {

    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final CiudadRepositoryPort ciudadRepositoryPort;
    private final PaisRepositoryPort paisRepositoryPort;
    private final HashServicePort hashService;
    private final CorreoServicePort correoService;

    public GestionarAdministradorService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            PaisRepositoryPort paisRepositoryPort,
            CiudadRepositoryPort ciudadRepositoryPort,
            HashServicePort hashService,
            CorreoServicePort correoService) {
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.paisRepositoryPort = paisRepositoryPort;
        this.ciudadRepositoryPort = ciudadRepositoryPort;
        this.hashService = hashService;
        this.correoService = correoService;
    }

    @Override
    public PaginaResult<AdministradorResult> listar(String search, Boolean activo, PaginaQuery query) {
        var pagina = usuarioRepository.buscarAdministradores(search, activo, query);
        var items = pagina.items().stream().map(this::toResult).toList();
        return new PaginaResult<>(items, pagina.total(), pagina.pagina(), pagina.tamano());
    }

    @Override
    @Transactional
    public AdministradorResult crear(CrearAdministradorCommand cmd) {
        if (usuarioRepository.existePorCorreo(cmd.correo()))
            throw UsuarioException.correoYaRegistrado();

        var usuario = new UsuarioModel(
            cmd.nombres(), cmd.apellidos(), cmd.correo(),
            cmd.telefono(), Rol.ADMINISTRADOR_CONDOMINIO,
            hashService.hashear(cmd.contrasena())
        );
        usuario = usuarioRepository.guardar(usuario);
        try {
            correoService.enviarBienvenida(
                cmd.correo(),
                cmd.nombres() + " " + cmd.apellidos(),
                cmd.contrasena());
        } catch (Exception e) {
            System.err.printf("Error al enviar correo de bienvenida a %s: %s%n",
                    cmd.correo(), e.getMessage());
        }
        return toResult(usuario);
    }

    @Override
    @Transactional
    public AdministradorResult actualizar(Long id, ActualizarAdministradorCommand cmd) {
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        usuario.actualizar(cmd.nombres(), cmd.apellidos(), cmd.telefono());
        if (cmd.rol() != null) {
            usuario.cambiarRol(Rol.valueOf(cmd.rol()));
        }
        return toResult(usuarioRepository.guardar(usuario));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (usuarioRepository.buscarPorId(id).isEmpty())
            throw UsuarioException.noEncontrado();
        usuarioRepository.eliminarPorId(id);
    }

    @Override
    @Transactional
    public void activarDesactivar(Long id, Boolean activo) {
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        if (Boolean.TRUE.equals(activo)) {
            usuario.activar();
        } else {
            usuario.desactivar();
            usuario.desasignarCondominio();
        }
        usuarioRepository.guardar(usuario);
    }

    @Override
    @Transactional
    public void asignarCondominio(Long id, Long idCondominio) {
        var usuario = usuarioRepository.buscarPorId(id)
            .orElseThrow(UsuarioException::noEncontrado);
        if (idCondominio == null) {
            usuario.desasignarCondominio();
        } else {
            if (usuario.getIdCondominio() != null) {
                if (usuario.getIdCondominio().equals(idCondominio)) {
                    usuarioRepository.guardar(usuario);
                    return;
                }
                usuario.desasignarCondominio();
            }
            if (usuario.getRol() == Rol.ADMINISTRADOR_CONDOMINIO && 
                usuarioRepository.buscarPorCondominioId(idCondominio).isPresent())
                throw CondominioException.yaTieneAdministradorAsignado();
            var condominio = condominioRepository.buscarPorId(idCondominio)
                .orElseThrow(CondominioException::noEncontrado);
            if (!Boolean.TRUE.equals(condominio.getActivo()))
                throw CondominioException.condominioInactivo();
            usuario.asignarCondominio(idCondominio);
        }
        usuarioRepository.guardar(usuario);
    }

    @Override
    public List<AdministradorResult> listarDisponibles() {
        return usuarioRepository.buscarAdministradoresSinCondominio()
            .stream()
            .map(this::toResult)
            .toList();
    }

    @Override
    public List<CondominioSimpleResult> listarCondominiosDisponibles() {
        return condominioRepository.buscarActivosSinAdministrador()
            .stream()
            .map(c -> {
                String nombrePais = paisRepositoryPort.buscarPorId(c.getIdPais())
                    .map(p -> p.nombre()).orElse(null);
                String nombreCiudad = ciudadRepositoryPort.buscarPorId(c.getIdCiudad())
                    .map(cd -> cd.nombre()).orElse(null);
                return new CondominioSimpleResult(
                    c.getId(), c.getNombre(), c.getDireccion(),
                    nombrePais, nombreCiudad, null, c.getFechaCreacion()
                );
            })
            .toList();
    }

    private AdministradorResult toResult(UsuarioModel u) {
        String nombreCondominio = null;
        if (u.getIdCondominio() != null) {
            nombreCondominio = condominioRepository.buscarNombrePorId(u.getIdCondominio()).orElse(null);
        }
        return new AdministradorResult(
            u.getId(), u.getNombres(), u.getApellidos(),
            u.getCorreo().direccion(),
            u.getTelefono() != null ? u.getTelefono().numero() : null,
            u.getActivo(), u.getIdCondominio(), nombreCondominio,
            u.getFechaCreacion()
        );
    }
}
