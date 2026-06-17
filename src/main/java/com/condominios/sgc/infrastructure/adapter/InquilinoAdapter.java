package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.domain.filter.InquilinoFilter;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;
import com.condominios.sgc.domain.port.InquilinoPort;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.InquilinoMapper;
import com.condominios.sgc.infrastructure.persistence.repository.InquilinoRepository;
import com.condominios.sgc.infrastructure.persistence.specification.InquilinoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public InquilinoModel guardar(InquilinoModel inquilino) {
        InquilinoEntity entidad = mapper.aEntidad(inquilino);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<InquilinoModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Pagina<InquilinoModel> obtenerTodos(Paginable request, InquilinoFilter filtro) {
        PageRequest pageRequest = PageRequest.of(request.pagina(), request.tamano());
        Page<InquilinoEntity> page = repository.findAll(InquilinoSpecification.conFiltro(filtro), pageRequest);
        var contenido = page.getContent().stream().map(mapper::aModelo).toList();
        return new Pagina<>(contenido, page.getNumber(), page.getSize(), page.getTotalElements(), page.getTotalPages());
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
    public int contarPorApartamento(Long idApartamento) {
        return repository.findByIdApartamento(idApartamento).size();
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
