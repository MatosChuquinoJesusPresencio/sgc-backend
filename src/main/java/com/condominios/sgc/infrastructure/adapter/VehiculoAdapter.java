package com.condominios.sgc.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.domain.port.VehiculoPort;
import com.condominios.sgc.infrastructure.persistence.entity.VehiculoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.EstacionamientoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;

@Component
public class VehiculoAdapter implements VehiculoPort {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InquilinoRepository inquilinoRepository;
    private final EstacionamientoRepository estacionamientoRepository;

    public VehiculoAdapter(VehiculoRepository vehiculoRepository, UsuarioRepository usuarioRepository,
            InquilinoRepository inquilinoRepository, EstacionamientoRepository estacionamientoRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.estacionamientoRepository = estacionamientoRepository;
    }

    @Override
    public Optional<VehiculoModel> findById(Long id) {
        return vehiculoRepository.findById(id).map(VehiculoMapper::toModel);
    }

    @Override
    public List<VehiculoModel> findAll() {
        return vehiculoRepository.findAll().stream()
                .map(VehiculoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoModel> findByPropietarioInquilinoId(Long inquilinoId) {
        return vehiculoRepository.findByInquilinoId(inquilinoId).stream()
                .map(VehiculoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoModel> findByPropietarioUsuarioId(String usuarioId) {
        return vehiculoRepository.findByPropietarioId(usuarioId).stream()
                .map(VehiculoMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public VehiculoModel save(VehiculoModel vehiculo) {
        VehiculoEntity entity = VehiculoMapper.toEntity(vehiculo);

        if (vehiculo.getPropietarioId() != null) {
            entity.setPropietario(usuarioRepository.getReferenceById(vehiculo.getPropietarioId()));
        } else {
            entity.setPropietario(null);
        }

        if (vehiculo.getInquilinoId() != null) {
            entity.setInquilino(inquilinoRepository.getReferenceById(vehiculo.getInquilinoId()));
        } else {
            entity.setInquilino(null);
        }

        if (vehiculo.getEstacionamientoId() != null) {
            entity.setEstacionamiento(estacionamientoRepository.getReferenceById(vehiculo.getEstacionamientoId()));
        } else {
            entity.setEstacionamiento(null);
        }

        VehiculoEntity saved = vehiculoRepository.save(entity);
        return VehiculoMapper.toModel(saved);
    }

    @Override
    public void deleteById(Long id) {
        vehiculoRepository.deleteById(id);
    }
}
