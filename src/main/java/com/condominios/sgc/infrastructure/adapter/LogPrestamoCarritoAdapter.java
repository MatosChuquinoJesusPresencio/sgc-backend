package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.LogPrestamoCarritoFilter;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.port.LogPrestamoCarritoPort;
import com.condominios.sgc.infrastructure.persistence.entity.LogPrestamoCarritoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.LogPrestamoCarritoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.LogPrestamoCarritoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.LogPrestamoCarritoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public LogPrestamoCarritoModel guardar(LogPrestamoCarritoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<LogPrestamoCarritoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<LogPrestamoCarritoModel> obtenerTodos(PaginacionRequest request, LogPrestamoCarritoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<LogPrestamoCarritoEntity> pagina = repository.findAll(LogPrestamoCarritoSpecification.conFiltro(filtro), pageable);
        List<LogPrestamoCarritoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
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
