package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;
import com.condominios.sgc.infrastructure.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.InquilinoSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InquilinoAdapter implements InquilinoPort {

    private final InquilinoRepository inquilinoRepository;
    private final ApartamentoRepository apartamentoRepository;

    public InquilinoAdapter(InquilinoRepository inquilinoRepository,
                            ApartamentoRepository apartamentoRepository) {
        this.inquilinoRepository = inquilinoRepository;
        this.apartamentoRepository = apartamentoRepository;
    }

    @Override
    public Optional<InquilinoModel> findById(Long id) {
        return inquilinoRepository.findById(id).map(InquilinoMapper::toModel);
    }

    @Override
    public PaginacionResponse<InquilinoModel> findByApartamentoId(Long apartamentoId, PaginacionRequest request) {
        var page = inquilinoRepository.findAll(
            InquilinoSpecifications.porApartamentoId(apartamentoId),
            PaginacionUtil.toPageable(request)
        );
        return PaginacionUtil.toPaginacionResponse(page, page.map(InquilinoMapper::toModel).toList());
    }

    @Override
    public InquilinoModel save(InquilinoModel model) {
        var entity = InquilinoMapper.toEntity(model);
        entity.setApartamento(apartamentoRepository.findById(model.getApartamentoId())
            .orElseThrow(() -> new RuntimeException("Apartamento no encontrado: " + model.getApartamentoId())));
        var saved = inquilinoRepository.save(entity);
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
