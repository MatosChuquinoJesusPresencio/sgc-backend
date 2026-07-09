package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.model.VehiculoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.VehiculoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.VehiculoJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class VehiculoRepositoryAdapter implements VehiculoRepositoryPort {

    private final VehiculoJpaRepository repository;

    public VehiculoRepositoryAdapter(VehiculoJpaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<VehiculoModel> buscarPorId(Long id) {
        return repository.findById(id).map(VehiculoMapper::toModel);
    }

    @Override
    public VehiculoModel guardar(VehiculoModel modelo) {
        return VehiculoMapper.toModel(repository.save(VehiculoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long contarPorCondominio(Long idCondominio) {
        return repository.countByIdCondominio(idCondominio);
    }

    @Transactional(readOnly = true)
    @Override
    public List<VehiculoModel> buscarPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario)
                .stream()
                .map(VehiculoMapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<VehiculoModel> buscarPorInquilino(Long idInquilino) {
        return repository.findByIdInquilino(idInquilino)
                .stream()
                .map(VehiculoMapper::toModel)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<VehiculoModel> buscarPorPlaca(String placa) {
        return repository.findByPlaca(placa).map(VehiculoMapper::toModel);
    }

    @Override
    public void eliminarPorInquilino(Long idInquilino) {
        repository.deleteByIdInquilino(idInquilino);
    }
}
