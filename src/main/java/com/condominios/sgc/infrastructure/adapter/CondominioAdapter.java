package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.CondominioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
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
    public CondominioModel guardar(CondominioModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<CondominioModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<CondominioModel> obtenerTodos(PaginacionRequest request, CondominioFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<CondominioEntity> pagina = repository.findAll(CondominioSpecification.conFiltro(filtro), pageable);
        List<CondominioModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
