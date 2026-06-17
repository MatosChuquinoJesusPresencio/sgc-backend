package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.PaisModel;
import com.condominios.sgc.domain.port.PaisPort;
import com.condominios.sgc.infrastructure.persistence.mapper.PaisMapper;
import com.condominios.sgc.infrastructure.persistence.repository.PaisRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PaisAdapter implements PaisPort {

    private final PaisRepository repository;
    private final PaisMapper mapper;

    public PaisAdapter(PaisRepository repository, PaisMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<PaisModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public List<PaisModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }
}
