package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.InquilinoFilter;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.port.InquilinoPort;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.InquilinoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class InquilinoAdapter implements InquilinoPort {

    private final InquilinoRepository repository;
    private final InquilinoMapper mapper;

    public InquilinoAdapter(InquilinoRepository repository, InquilinoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public InquilinoModel guardar(InquilinoModel modelo) {
        var entidad = mapper.aEntidad(modelo);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<InquilinoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public PaginacionResponse<InquilinoModel> obtenerTodos(PaginacionRequest request, InquilinoFilter filtro) {
        Pageable pageable = PageRequest.of(request.pagina(), request.tamano());
        Page<InquilinoEntity> pagina = repository.findAll(InquilinoSpecification.conFiltro(filtro), pageable);
        List<InquilinoModel> contenido = pagina.getContent().stream().map(mapper::aModelo).toList();
        return new PaginacionResponse<>(contenido, pagina.getNumber(), pagina.getSize(),
            pagina.getTotalElements(), pagina.getTotalPages());
    }

    @Override
    public Optional<InquilinoModel> obtenerPorDocumento(TipoDocumento tipoDocumento, String numeroDocumento) {
        return repository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento).map(mapper::aModelo);
    }

    @Override
    public List<InquilinoModel> obtenerPorApartamento(Long idApartamento) {
        return repository.findByIdApartamento(idApartamento).stream().map(mapper::aModelo).toList();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
