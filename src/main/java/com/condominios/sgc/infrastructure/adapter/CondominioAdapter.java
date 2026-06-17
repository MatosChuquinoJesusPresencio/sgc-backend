package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.CondominioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CondominioAdapter implements CondominioPort {

    private final CondominioRepository repository;
    private final CondominioMapper mapper;

    public CondominioAdapter(CondominioRepository repository, CondominioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CondominioModel guardar(CondominioModel condominio) {
        CondominioEntity entidad = mapper.aEntidad(condominio);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<CondominioModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Pagina<CondominioModel> obtenerTodos(Paginable paginable, CondominioFilter filtro) {
        PageRequest pageRequest = PageRequest.of(paginable.pagina(), paginable.tamano());
        Page<CondominioEntity> page = repository.findAll(CondominioSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
