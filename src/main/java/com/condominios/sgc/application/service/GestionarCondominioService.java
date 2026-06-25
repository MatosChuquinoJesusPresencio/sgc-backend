package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarCondominioCommand;
import com.condominios.sgc.application.dto.command.CrearCondominioCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.CondominioResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarCondominioUseCase;
import com.condominios.sgc.application.port.out.CiudadRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.PaisRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;

public class GestionarCondominioService implements GestionarCondominioUseCase {

    private final CondominioRepositoryPort condominioRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final PaisRepositoryPort paisRepository;
    private final CiudadRepositoryPort ciudadRepository;

    public GestionarCondominioService(
            CondominioRepositoryPort condominioRepository,
            UsuarioRepositoryPort usuarioRepository,
            PaisRepositoryPort paisRepository,
            CiudadRepositoryPort ciudadRepository) {
        this.condominioRepository = condominioRepository;
        this.usuarioRepository = usuarioRepository;
        this.paisRepository = paisRepository;
        this.ciudadRepository = ciudadRepository;
    }

    @Override
    public PaginaResult<CondominioResult> listar(String search, Boolean activo, PaginaQuery query) {
        var condominios = condominioRepository.buscarTodos(search, activo, query.pagina(), query.tamano());
        long total = condominioRepository.contarTodos(search, activo);
        var items = condominios.stream().map(this::toResult).toList();
        return new PaginaResult<>(items, total, query.pagina(), query.tamano());
    }

    @Override
    public CondominioResult crear(CrearCondominioCommand cmd) {
        condominioRepository.buscarPorNombre(cmd.nombre())
            .ifPresent(c -> { throw CondominioException.nombreYaExiste(cmd.nombre()); });
        var condominio = new CondominioModel(cmd.nombre(), cmd.idPais(), cmd.idCiudad(), cmd.direccion());
        condominio.activar();
        var guardado = condominioRepository.guardar(condominio);
        return toResult(guardado);
    }

    @Override
    public CondominioResult actualizar(Long id, ActualizarCondominioCommand cmd) {
        var condominio = condominioRepository.buscarPorId(id)
            .orElseThrow(CondominioException::noEncontrado);
        if (!condominio.getNombre().equals(cmd.nombre())) {
            condominioRepository.buscarPorNombre(cmd.nombre())
                .ifPresent(c -> { throw CondominioException.nombreYaExiste(cmd.nombre()); });
        }
        condominio.actualizar(cmd.nombre(), cmd.idPais(), cmd.idCiudad(), cmd.direccion());
        return toResult(condominioRepository.guardar(condominio));
    }

    @Override
    public void eliminar(Long id) {
        if (condominioRepository.buscarPorId(id).isEmpty())
            throw CondominioException.noEncontrado();
        condominioRepository.eliminarPorId(id);
    }

    @Override
    public void activarDesactivar(Long id, Boolean activo) {
        var condominio = condominioRepository.buscarPorId(id)
            .orElseThrow(CondominioException::noEncontrado);
        if (Boolean.TRUE.equals(activo)) {
            condominio.activar();
        } else {
            condominio.desactivar();
        }
        condominioRepository.guardar(condominio);
    }

    private CondominioResult toResult(CondominioModel c) {
        String nombrePais = paisRepository.buscarPorId(c.getIdPais())
            .map(p -> p.nombre()).orElse(null);
        String nombreCiudad = ciudadRepository.buscarPorId(c.getIdCiudad())
            .map(ci -> ci.nombre()).orElse(null);
        var admin = usuarioRepository.buscarPorCondominioId(c.getId());
        Long idAdmin = admin.map(a -> a.getId()).orElse(null);
        String nombreAdmin = admin.map(a -> a.getNombres() + " " + a.getApellidos()).orElse(null);
        return new CondominioResult(
            c.getId(), c.getNombre(), c.getIdPais(), nombrePais,
            c.getIdCiudad(), nombreCiudad, c.getDireccion(),
            c.getActivo(), idAdmin, nombreAdmin, c.getFechaCreacion()
        );
    }
}
