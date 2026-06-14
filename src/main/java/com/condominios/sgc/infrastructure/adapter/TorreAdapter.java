package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.TorreFilter;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.port.TorrePort;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.TorreMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import com.condominios.sgc.infrastructure.persistence.specification.TorreSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public TorreModel guardar(TorreModel modelo) {
        var entidad = mapper.aEntidad(modelo);
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
    public PaginacionResponse<TorreModel> obtenerTodos(PaginacionRequest request, TorreFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<TorreEntity> pagina = repository.findAll(TorreSpecification.conFiltro(filtro), pageable);
        List<TorreModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
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
