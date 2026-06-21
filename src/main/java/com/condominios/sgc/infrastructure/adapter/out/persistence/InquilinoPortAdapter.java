package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.InquilinoJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class InquilinoPortAdapter implements InquilinoRepositoryPort {

    private final InquilinoJpaRepository repository;

    public InquilinoPortAdapter(InquilinoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<InquilinoModel> buscarPorId(Long id) {
        return repository.findById(id).map(InquilinoMapper::toModel);
    }

    @Override
    public InquilinoModel guardar(InquilinoModel modelo) {
        return InquilinoMapper.toModel(repository.save(InquilinoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
