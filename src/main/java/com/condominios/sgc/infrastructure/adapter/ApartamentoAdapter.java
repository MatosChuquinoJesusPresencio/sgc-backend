package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.port.ApartamentoPort;
import com.condominios.sgc.infrastructure.persistence.entity.ApartamentoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.ApartamentoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.ApartamentoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ApartamentoAdapter implements ApartamentoPort {

    private final ApartamentoRepository repository;
    private final ApartamentoMapper mapper;

    public ApartamentoAdapter(ApartamentoRepository repository, ApartamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ApartamentoModel guardar(ApartamentoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<ApartamentoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<ApartamentoModel> obtenerTodos(PaginacionRequest request, ApartamentoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<ApartamentoEntity> pagina = repository.findAll(ApartamentoSpecification.conFiltro(filtro), pageable);
        List<ApartamentoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public int contarPorCondominio(Long idCondominio) {
        return (int) repository.count(ApartamentoSpecification.porCondominio(idCondominio));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
