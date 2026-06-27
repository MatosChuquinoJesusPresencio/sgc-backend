package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.CondominioJpaRepository;

@Component
@Transactional(readOnly = true)
public class CondominioRepositoryAdapter implements CondominioRepositoryPort {

    private final CondominioJpaRepository repository;

    public CondominioRepositoryAdapter(CondominioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CondominioModel> buscarPorId(Long id) {
        return repository.findById(id).map(CondominioMapper::toModel);
    }

    @Override
    public Optional<CondominioModel> buscarPorNombre(String nombre) {
        return repository.findByNombre(nombre).map(CondominioMapper::toModelLigero);
    }

    @Transactional
    @Override
    public CondominioModel guardar(CondominioModel modelo) {
        CondominioEntity entity = CondominioMapper.toEntity(modelo);
        if (entity.getTorres() != null) {
            entity.getTorres().forEach(t -> t.setCondominio(entity));
        }
        if (entity.getConfiguracion() != null) {
            entity.getConfiguracion().setCondominio(entity);
        }
        return CondominioMapper.toModel(repository.save(entity));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<CondominioModel> buscarActivosSinAdministrador() {
        return repository.buscarActivosSinAdministrador()
            .stream()
            .map(CondominioMapper::toModelLigero)
            .toList();
    }

    @Override
    public Optional<String> buscarNombrePorId(Long id) {
        return repository.findNombreById(id);
    }

    @Override
    public PaginaResult<CondominioModel> buscarTodos(String search, Boolean activo, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var page = repository.buscarTodos(search, activo, pageable);
        var items = page.getContent().stream().map(CondominioMapper::toModelLigero).toList();
        return new PaginaResult<>(items, page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public long contarTodos(String search, Boolean activo) {
        return repository.contarTodos(search, activo);
    }

    @Override
    public List<CondominioModel> buscarRecientes(int limite) {
        var pageable = PageRequest.of(0, limite);
        return repository.buscarRecientes(pageable)
            .stream()
            .map(CondominioMapper::toModelLigero)
            .toList();
    }

    @Override
    public long contarActivos() {
        return repository.contarActivos();
    }
}
