package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.InquilinoJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class InquilinoRepositoryAdapter implements InquilinoRepositoryPort {

    private final InquilinoJpaRepository repository;

    public InquilinoRepositoryAdapter(InquilinoJpaRepository repository) {
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

    @Override
    public List<InquilinoModel> buscarPorApartamento(Long idApartamento) {
        return repository.findByIdApartamento(idApartamento).stream()
            .map(InquilinoMapper::toModel)
            .toList();
    }

    @Override
    public void eliminarPorApartamento(Long idApartamento) {
        repository.deleteByIdApartamento(idApartamento);
    }
}
