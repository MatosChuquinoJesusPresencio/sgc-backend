package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.TorreFilter;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import com.condominios.sgc.infrastructure.persistence.specification.TorreSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class TorreAdapter implements TorrePort {

    private final TorreRepository repository;
    private final TorreMapper mapper;

    public TorreAdapter(TorreRepository repository, TorreMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TorreModel guardar(TorreModel torre) {
        TorreEntity entidad = mapper.aEntidad(torre);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<TorreModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public List<TorreModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public Pagina<TorreModel> obtenerTodos(Paginable paginable, TorreFilter filtro) {
        PageRequest pageRequest = PageRequest.of(paginable.pagina(), paginable.tamano());
        Page<TorreEntity> page = repository.findAll(TorreSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public List<TorreModel> obtenerPorCondominio(Long idCondominio) {
        return repository.findByIdCondominio(idCondominio).stream().map(mapper::aModelo).toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
