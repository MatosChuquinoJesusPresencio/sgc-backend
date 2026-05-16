package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VehiculoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.UsuarioSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class UsuarioAdapter implements UsuarioPort {

    private final UsuarioRepository usuarioRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final VehiculoRepository vehiculoRepository;

    public UsuarioAdapter(
            UsuarioRepository usuarioRepository,
            ApartamentoRepository apartamentoRepository,
            VehiculoRepository vehiculoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    @Override
    public Optional<UsuarioModel> findById(String id) {
        return usuarioRepository.findById(id).map(UsuarioMapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).map(UsuarioMapper::toModel);
    }

    @Override
    public boolean existsByCorreo(String correo) {
        return usuarioRepository.existsByCorreo(correo);
    }

    @Override
    public UsuarioModel save(UsuarioModel model) {
        var entity = UsuarioMapper.toEntity(model);
        var saved = usuarioRepository.save(entity);
        return UsuarioMapper.toModel(saved);
    }

    @Override
    public void deleteById(String id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public PaginacionResponse<UsuarioModel> buscarPaginado(PaginacionRequest request) {
        var orden = request.direccion() != null && request.direccion().equalsIgnoreCase("DESC")
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = request.ordenarPor() != null
                ? PageRequest.of(request.pagina(), request.tamanio(), orden, request.ordenarPor())
                : PageRequest.of(request.pagina(), request.tamanio());
        var spec = UsuarioSpecifications.fromFiltros(request.filtros());
        Page<com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity> page =
                usuarioRepository.findAll(spec, pageable);
        return PaginacionResponse.of(
                page.getContent().stream().map(UsuarioMapper::toModel).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    @Override
    public boolean esPropietarioDeApartamento(String usuarioId) {
        return apartamentoRepository.findByPropietarioId(usuarioId).isPresent();
    }

    @Override
    public boolean tieneVehiculosAsociados(String usuarioId) {
        return !vehiculoRepository.findByPropietarioId(usuarioId).isEmpty();
    }
}
