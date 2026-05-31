package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CarritoModel;
import com.condominios.sgc.domain.port.CarritoPort;
import com.condominios.sgc.infrastructure.persistence.mapper.CarritoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CarritoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.specification.CarritoSpecifications;
import com.condominios.sgc.infrastructure.util.PaginacionUtil;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CarritoAdapter implements CarritoPort {

    private final CarritoRepository carritoRepository;
    private final CondominioRepository condominioRepository;

    public CarritoAdapter(CarritoRepository carritoRepository, CondominioRepository condominioRepository) {
        this.carritoRepository = carritoRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public Optional<CarritoModel> findById(Long id) {
        return carritoRepository.findById(id).map(CarritoMapper::toModel);
    }

    @Override
    public PaginacionResponse<CarritoModel> findByCondominioId(Long condominioId, PaginacionRequest request) {
        var spec = CarritoSpecifications.porCondominioId(condominioId);
        var page = carritoRepository.findAll(spec, PaginacionUtil.toPageable(request));
        return PaginacionUtil.toPaginacionResponse(page, page.map(CarritoMapper::toModel).toList());
    }

    @Override
    public CarritoModel save(CarritoModel model) {
        var entity = CarritoMapper.toEntity(model);
        entity.setCondominio(condominioRepository.findById(model.getCondominioId())
            .orElseThrow(() -> new RuntimeException("Condominio no encontrado: " + model.getCondominioId())));
        var saved = carritoRepository.save(entity);
        return CarritoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        carritoRepository.deleteById(id);
    }
}
