package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.persistence.repository.LogAccesoVehicularRepository;
import com.condominios.sgc.infrastructure.persistence.specification.LogAccesoVehicularSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class LogAccesoVehicularAdapter implements LogAccesoVehicularPort {

    private final LogAccesoVehicularRepository repository;
    private final LogAccesoVehicularMapper mapper;

    public LogAccesoVehicularAdapter(LogAccesoVehicularRepository repository, LogAccesoVehicularMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public LogAccesoVehicularModel guardar(LogAccesoVehicularModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<LogAccesoVehicularModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<LogAccesoVehicularModel> obtenerTodos(PaginacionRequest request, LogAccesoVehicularFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<LogAccesoVehicularEntity> pagina = repository.findAll(LogAccesoVehicularSpecification.conFiltro(filtro), pageable);
        List<LogAccesoVehicularModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }
}
