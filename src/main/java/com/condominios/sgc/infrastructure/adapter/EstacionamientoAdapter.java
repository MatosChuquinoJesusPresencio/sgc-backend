package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.EstacionamientoFilter;
import com.condominios.sgc.domain.model.EstacionamientoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.EstacionamientoPort;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.EstacionamientoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.EstacionamientoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public EstacionamientoModel guardar(EstacionamientoModel estacionamiento) {
        EstacionamientoEntity entidad = mapper.aEntidad(estacionamiento);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<EstacionamientoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Pagina<EstacionamientoModel> obtenerTodos(Paginable request, EstacionamientoFilter filtro) {
        PageRequest pageRequest = PageRequest.of(request.pagina(), request.tamano());
        Page<EstacionamientoEntity> page = repository.findAll(EstacionamientoSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public int contarPorCondominio(Long idCondominio) {
        return (int) repository.count(EstacionamientoSpecification.porCondominio(idCondominio));
    }

    @Override
    public int contarPorApartamento(Long idApartamento) {
        return (int) repository.count(EstacionamientoSpecification.porApartamento(idApartamento));
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
