package com.condominios.sgc.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.condominios.sgc.domain.dto.PageRequestDto;
import com.condominios.sgc.domain.dto.PageResponseDto;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.port.CondominioPort;
import com.condominios.sgc.infrastructure.persistence.entity.CarritoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.ConfiguracionEntity;
import com.condominios.sgc.infrastructure.persistence.entity.EstacionamientoEntity;
import com.condominios.sgc.infrastructure.persistence.entity.TorreEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CarritoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.ConfiguracionRepository;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.TorreRepository;
import com.condominios.sgc.infrastructure.persistence.specification.CarritoSpecifications;
import com.condominios.sgc.infrastructure.persistence.specification.CondominioSpecifications;
import com.condominios.sgc.infrastructure.persistence.specification.EstacionamientoSpecifications;

public class CondominioAdapter implements CondominioPort {

    private final CondominioRepository condominioRepository;
    private final ConfiguracionRepository configuracionRepository;
    private final TorreRepository torreRepository;
    private final EstacionamientoRepository estacionamientoRepository;
    private final CarritoRepository carritoRepository;

    public CondominioAdapter(
            CondominioRepository condominioRepository,
            ConfiguracionRepository configuracionRepository,
            TorreRepository torreRepository,
            EstacionamientoRepository estacionamientoRepository,
            CarritoRepository carritoRepository) {
        this.condominioRepository = condominioRepository;
        this.configuracionRepository = configuracionRepository;
        this.torreRepository = torreRepository;
        this.estacionamientoRepository = estacionamientoRepository;
        this.carritoRepository = carritoRepository;
    }

    @Override
    public Optional<CondominioModel> findById(Long id) {
        return condominioRepository.findById(id).map(this::mapToModelWithLists);
    }

    @Override
    public PageResponseDto<CondominioModel> findAll(PageRequestDto request) {
        Sort.Direction direction = request.direccion() != null ? Sort.Direction.valueOf(request.direccion().name()) : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, request.ordenar());
        PageRequest pageRequest = PageRequest.of(request.pagina(), request.tamano(), sort);
        
        Specification<CondominioEntity> spec = CondominioSpecifications.fromFiltros(request.filtros());
        
        Page<CondominioEntity> page = condominioRepository.findAll(spec, pageRequest);
        
        List<CondominioModel> content = page.getContent().stream()
                .map(this::mapToModelWithLists)
                .toList();
                
        return PageResponseDto.of(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    @Override
    public CondominioModel save(CondominioModel model) {
        ConfiguracionEntity configuracion = null;
        if (model.getConfiguracionId() != null) {
            configuracion = configuracionRepository.findById(model.getConfiguracionId())
                    .orElseThrow(() -> new IllegalArgumentException("Configuracion no encontrada"));
        }
        
        CondominioEntity entity = CondominioMapper.toEntity(model, configuracion);
        CondominioEntity savedEntity = condominioRepository.save(entity);
        return mapToModelWithLists(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        condominioRepository.deleteById(id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return condominioRepository.existsByNombre(nombre);
    }

    private CondominioModel mapToModelWithLists(CondominioEntity entity) {
        CondominioModel model = CondominioMapper.toModel(entity);
        
        List<Long> torreIds = torreRepository.findByCondominioId(entity.getId())
                .stream().map(TorreEntity::getId).toList();
        torreIds.forEach(model::agregarTorre);
        
        List<Long> estacionamientoIds = estacionamientoRepository.findAll(EstacionamientoSpecifications.porCondominioId(entity.getId()))
                .stream().map(EstacionamientoEntity::getId).toList();
        estacionamientoIds.forEach(model::agregarEstacionamiento);
        
        List<Long> carritoIds = carritoRepository.findAll(CarritoSpecifications.porCondominioId(entity.getId()))
                .stream().map(CarritoEntity::getId).toList();
        carritoIds.forEach(model::agregarCarrito);
        
        return model;
    }
}
