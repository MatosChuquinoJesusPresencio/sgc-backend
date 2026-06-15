package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.EstacionamientoFilter;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.port.EstacionamientoPort;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.EstacionamientoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.EstacionamientoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class EstacionamientoAdapter implements EstacionamientoPort {

    private final EstacionamientoRepository repository;
    private final EstacionamientoMapper mapper;

    public EstacionamientoAdapter(EstacionamientoRepository repository, EstacionamientoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EstacionamientoModel guardar(EstacionamientoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<EstacionamientoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<EstacionamientoModel> obtenerTodos(PaginacionRequest request, EstacionamientoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<EstacionamientoEntity> pagina = repository.findAll(EstacionamientoSpecification.conFiltro(filtro), pageable);
        List<EstacionamientoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public int contarPorCondominio(Long idCondominio) {
        return (int) repository.count(EstacionamientoSpecification.porCondominio(idCondominio));
    }

    @Override
    public List<EstacionamientoModel> obtenerPorApartamento(Long idApartamento) {
        return repository.findByIdApartamento(idApartamento).stream().map(mapper::aModelo).toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
