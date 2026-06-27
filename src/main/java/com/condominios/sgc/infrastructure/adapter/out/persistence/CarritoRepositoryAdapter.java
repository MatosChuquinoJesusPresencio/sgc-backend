package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.CarritoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.CarritoJpaRepository;

@Component
public class CarritoRepositoryAdapter implements CarritoRepositoryPort {

    private final CarritoJpaRepository repository;

    public CarritoRepositoryAdapter(CarritoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CarritoModel> buscarPorId(Long id) {
        return repository.findById(id).map(CarritoMapper::toModel);
    }

    @Override
    public CarritoModel guardar(CarritoModel modelo) {
        return CarritoMapper.toModel(repository.save(CarritoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long contarPorCondominio(Long idCondominio) {
        return repository.countByIdCondominio(idCondominio);
    }

    @Override
    public PaginaResult<CarritoModel> buscarPorCondominio(Long idCondominio, PaginaQuery paginacion) {
        var pageable = PageRequest.of(paginacion.pagina(), paginacion.tamano());
        var page = repository.findByIdCondominio(idCondominio, pageable);
        var items = page.getContent().stream().map(CarritoMapper::toModel).toList();
        return new PaginaResult<>(items, page.getTotalElements(), page.getNumber(), page.getSize());
    }

    @Override
    public Optional<CarritoModel> buscarPorCodigo(String codigo) {
        return repository.findByCodigo(codigo).map(CarritoMapper::toModel);
    }
}
