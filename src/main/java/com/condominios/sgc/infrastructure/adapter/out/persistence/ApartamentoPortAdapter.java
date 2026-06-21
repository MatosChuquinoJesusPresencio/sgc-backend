package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.ApartamentoJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ApartamentoPortAdapter implements ApartamentoRepositoryPort {

    private final ApartamentoJpaRepository repository;

    public ApartamentoPortAdapter(ApartamentoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ApartamentoModel> buscarPorId(Long id) {
        return repository.findById(id).map(ApartamentoMapper::toModel);
    }

    @Override
    public ApartamentoModel guardar(ApartamentoModel modelo) {
        return ApartamentoMapper.toModel(repository.save(ApartamentoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
