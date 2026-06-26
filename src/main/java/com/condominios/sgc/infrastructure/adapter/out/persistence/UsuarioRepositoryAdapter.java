package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UsuarioModel> buscarPorId(Long id) {
        return repository.findById(id).map(UsuarioMapper::toModel);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UsuarioModel> buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(UsuarioMapper::toModel);
    }

    @Override
    public UsuarioModel guardar(UsuarioModel modelo) {
        return UsuarioMapper.toModel(repository.save(UsuarioMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PaginaResult<UsuarioModel> buscarAdministradores(String search, Boolean activo, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var page = repository.buscarAdministradores(search, activo, pageable);
        var items = page.getContent().stream().map(UsuarioMapper::toModel).toList();
        return new PaginaResult<>(items, page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return repository.existsByCorreoIgnoreCase(correo);
    }

    @Override
    public List<UsuarioModel> buscarAdministradoresSinCondominio() {
        return repository.findByRolAndIdCondominioIsNull(Rol.ADMINISTRADOR_CONDOMINIO.name())
            .stream()
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UsuarioModel> buscarPorCondominioId(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).map(UsuarioMapper::toModel);
    }

    @Override
    public long contarPorRol(String rol) {
        return repository.countByRol(rol);
    }

    @Override
    public List<UsuarioModel> buscarRecientesPorRol(String rol, int limite) {
        var pageable = PageRequest.of(0, limite);
        return repository.buscarRecientesPorRol(rol, pageable)
            .stream()
            .map(UsuarioMapper::toModel)
            .toList();
    }

    @Override
    public PaginaResult<UsuarioModel> buscarTodos(String search, String rol, Boolean activo, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var page = repository.buscarTodos(search, rol, activo, pageable);
        var items = page.getContent().stream().map(UsuarioMapper::toModel).toList();
        return new PaginaResult<>(items, page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public long contarPorCondominioYRol(Long idCondominio, String rol) {
        return repository.countByIdCondominioAndRol(idCondominio, rol);
    }

    @Override
    public PaginaResult<UsuarioModel> buscarPorCondominio(Long idCondominio, String search, String rol, Boolean activo, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var page = repository.buscarPorCondominio(idCondominio, search, rol, activo, pageable);
        var items = page.getContent().stream().map(UsuarioMapper::toModel).toList();
        return new PaginaResult<>(items, page.getTotalElements(), page.getNumber(), page.getSize());
    }
}
