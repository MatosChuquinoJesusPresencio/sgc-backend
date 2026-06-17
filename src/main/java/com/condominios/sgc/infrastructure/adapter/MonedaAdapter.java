package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.MonedaModel;
import com.condominios.sgc.domain.port.MonedaPort;
import com.condominios.sgc.infrastructure.persistence.mapper.MonedaMapper;
import com.condominios.sgc.infrastructure.persistence.repository.MonedaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class MonedaAdapter implements MonedaPort {

    private final MonedaRepository repository;
    private final MonedaMapper mapper;

    public MonedaAdapter(MonedaRepository repository, MonedaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<MonedaModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }
}
