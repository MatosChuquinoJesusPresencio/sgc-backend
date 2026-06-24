package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.ApartamentoJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApartamentoRepositoryAdapter implements ApartamentoRepositoryPort {

    private final ApartamentoJpaRepository repository;

    public ApartamentoRepositoryAdapter(ApartamentoJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    @Override
    public List<ApartamentoModel> buscarPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario)
                .stream()
                .map(ApartamentoMapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApartamentoModel> buscarPorNumeroYCondominio(Integer numero, Long idCondominio) {
        return repository.findByNumeroAndCondominioId(numero, idCondominio)
                .map(ApartamentoMapper::toModel);
    }
}
