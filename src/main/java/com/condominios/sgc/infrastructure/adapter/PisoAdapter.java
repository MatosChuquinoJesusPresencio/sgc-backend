package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.PisoFilter;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.domain.port.PisoPort;
import com.condominios.sgc.infrastructure.persistence.entity.PisoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.PisoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.PisoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.PisoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PisoAdapter implements PisoPort {

    private final PisoRepository repository;
    private final PisoMapper mapper;

    public PisoAdapter(PisoRepository repository, PisoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PisoModel guardar(PisoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<PisoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public List<PisoModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public PaginacionResponse<PisoModel> obtenerTodos(PaginacionRequest request, PisoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<PisoEntity> pagina = repository.findAll(PisoSpecification.conFiltro(filtro), pageable);
        List<PisoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public List<PisoModel> obtenerPorTorre(Long idTorre) {
        return repository.findByIdTorre(idTorre).stream().map(mapper::aModelo).toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
