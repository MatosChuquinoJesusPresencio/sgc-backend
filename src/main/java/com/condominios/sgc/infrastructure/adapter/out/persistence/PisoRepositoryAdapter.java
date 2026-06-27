package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.PisoRepositoryPort;
import com.condominios.sgc.domain.model.PisoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.PisoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.PisoJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class PisoRepositoryAdapter implements PisoRepositoryPort {

    private final PisoJpaRepository repository;

    public PisoRepositoryAdapter(PisoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PisoModel> buscarPorId(Long id) {
        return repository.findById(id).map(PisoMapper::toModel);
    }

    @Override
    public PisoModel guardar(PisoModel modelo) {
        return PisoMapper.toModel(repository.save(PisoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
