package com.condominios.sgc.infrastructure.adapter.out.persistence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.AdminInquilinoResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.ApartamentoMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.ApartamentoJpaRepository;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.InquilinoJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApartamentoRepositoryAdapter implements ApartamentoRepositoryPort {

    private final ApartamentoJpaRepository repository;
    private final InquilinoJpaRepository inquilinoRepository;

    public ApartamentoRepositoryAdapter(
            ApartamentoJpaRepository repository,
            InquilinoJpaRepository inquilinoRepository) {
        this.repository = repository;
        this.inquilinoRepository = inquilinoRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApartamentoModel> buscarPorId(Long id) {
        return repository.findById(id).map(ApartamentoMapper::toModel);
    }

    @Override
    public ApartamentoModel guardar(ApartamentoModel modelo) {
        return ApartamentoMapper.toModel(repository.save(ApartamentoMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApartamentoModel> buscarPorPropietario(Long idPropietario) {
        return repository.findByIdPropietario(idPropietario)
                .map(ApartamentoMapper::toModel);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApartamentoModel> buscarPorNumeroYCondominio(Integer numero, Long idCondominio) {
        return repository.findByNumeroAndCondominioId(numero, idCondominio)
                .map(ApartamentoMapper::toModel);
    }

    @Override
    public PaginaResult<AdminApartamentoDetailResult> buscarEnCondominio(Long condominioId, PaginaQuery pagina) {
        var total = repository.countApartamentosByCondominio(condominioId);
        if (total == 0) {
            return new PaginaResult<>(List.of(), 0, pagina.pagina(), pagina.tamano());
        }

        var offset = pagina.pagina() * pagina.tamano();
        var rows = repository.findApartamentosPage(condominioId, offset, pagina.tamano());
        var inquilinos = cargarInquilinos(rows);
        var items = rows.stream().map(r -> toDetail(r, inquilinos)).toList();
        return new PaginaResult<>(items, total, pagina.pagina(), pagina.tamano());
    }

    private Map<Long, List<AdminInquilinoResult>> cargarInquilinos(List<Object[]> rows) {
        var ids = rows.stream()
                .map(r -> ((Number) r[0]).longValue())
                .toList();
        if (ids.isEmpty()) return Map.of();
        return inquilinoRepository.findByIdApartamentoIn(ids)
                .stream()
                .collect(Collectors.groupingBy(
                        e -> e.getIdApartamento(),
                        Collectors.mapping(
                                e -> new AdminInquilinoResult(
                                        e.getId(), e.getNombres(), e.getApellidos(),
                                        e.getTipoDocumento(), e.getNumeroDocumento()),
                                Collectors.toList())));
    }

    private AdminApartamentoDetailResult toDetail(Object[] row, Map<Long, List<AdminInquilinoResult>> inquilinosPorApto) {
        var id = ((Number) row[0]).longValue();
        var numero = (Integer) row[1];
        var metraje = (BigDecimal) row[2];
        var derechoEstacionamiento = (Boolean) row[3];
        var idPropietario = row[4] != null ? ((Number) row[4]).longValue() : null;
        var pisoNumero = (Integer) row[5];
        var torreNombre = (String) row[6];
        var nombrePropietario = row[7] != null ? row[7] + " " + (row[8] != null ? row[8] : "") : null;
        var inq = inquilinosPorApto.getOrDefault(id, List.of());
        return new AdminApartamentoDetailResult(
            id, numero, metraje, derechoEstacionamiento,
            idPropietario, nombrePropietario != null ? nombrePropietario.trim() : null,
            torreNombre, pisoNumero, inq);
    }
}
