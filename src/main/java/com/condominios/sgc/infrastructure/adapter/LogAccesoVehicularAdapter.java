package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.LogAccesoVehicularFilter;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.LogAccesoVehicularPort;
import com.condominios.sgc.infrastructure.persistence.entity.LogAccesoVehicularEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.LogAccesoVehicularMapper;
import com.condominios.sgc.infrastructure.persistence.repository.LogAccesoVehicularRepository;
import com.condominios.sgc.infrastructure.persistence.specification.LogAccesoVehicularSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
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
    public LogAccesoVehicularModel guardar(LogAccesoVehicularModel log) {
        LogAccesoVehicularEntity entidad = mapper.aEntidad(log);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<LogAccesoVehicularModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Pagina<LogAccesoVehicularModel> obtenerTodos(Paginable request, LogAccesoVehicularFilter filtro) {
        PageRequest pageRequest = PageRequest.of(request.pagina(), request.tamano());
        Page<LogAccesoVehicularEntity> page = repository.findAll(LogAccesoVehicularSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
