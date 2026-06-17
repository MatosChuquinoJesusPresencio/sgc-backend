package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.filter.LogPrestamoCarritoFilter;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.LogPrestamoCarritoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.LogPrestamoCarritoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class LogPrestamoCarritoAdapter implements LogPrestamoCarritoPort {

    private final LogPrestamoCarritoRepository repository;
    private final LogPrestamoCarritoMapper mapper;

    public LogPrestamoCarritoAdapter(LogPrestamoCarritoRepository repository, LogPrestamoCarritoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel log) {
        LogPrestamoCarritoEntity entidad = mapper.aEntidad(log);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<LogPrestamoCarritoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Pagina<LogPrestamoCarritoModel> obtenerTodos(Paginable request, LogPrestamoCarritoFilter filtro) {
        PageRequest pageRequest = PageRequest.of(request.pagina(), request.tamano());
        Page<LogPrestamoCarritoEntity> page = repository.findAll(LogPrestamoCarritoSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public List<LogPrestamoCarritoModel> obtenerSinDevolucion() {
        return repository.findByFechaDevolucionIsNull().stream().map(mapper::aModelo).toList();
    }

    @Override
    public long contarSinDevolucionPorApartamento(Long idApartamento) {
        return repository.countByFechaDevolucionIsNullAndIdApartamento(idApartamento);
    }
}
