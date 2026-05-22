package com.condominios.sgc.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;

@Component
public class InquilinoAdapter implements InquilinoPort {

    private final InquilinoRepository inquilinoRepository;
    private final ApartamentoRepository apartamentoRepository;

    public InquilinoAdapter(InquilinoRepository inquilinoRepository, ApartamentoRepository apartamentoRepository) {
        this.inquilinoRepository = inquilinoRepository;
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    public Optional<InquilinoModel> findById(Long id) {
        return inquilinoRepository.findById(id).map(InquilinoMapper::toModel);
    }

    @Override
    public List<InquilinoModel> findByApartamentoId(Long apartamentoId) {
        return inquilinoRepository.findByApartamentoId(apartamentoId).stream()
                .map(InquilinoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public InquilinoModel save(InquilinoModel inquilino) {
        InquilinoEntity entity = InquilinoMapper.toEntity(inquilino);
        
        if (inquilino.getApartamentoId() != null) {
            entity.setApartamento(apartamentoRepository.getReferenceById(inquilino.getApartamentoId()));
        }

        InquilinoEntity saved = inquilinoRepository.save(entity);
        return InquilinoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        inquilinoRepository.deleteById(id);
    }

    @Override
    public Integer countByApartamentoId(Long apartamentoId) {
        return inquilinoRepository.countByApartamentoId(apartamentoId);
    }
}
