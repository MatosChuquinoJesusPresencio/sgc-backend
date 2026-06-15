package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.InquilinoFilter;
import com.condominios.sgc.domain.model.InquilinoModel;
import java.util.List;
import java.util.Optional;

public interface InquilinoPort {
    InquilinoModel guardar(InquilinoModel inquilino);
    Optional<InquilinoModel> obtenerPorId(Long id);
    PaginacionResponse<InquilinoModel> obtenerTodos(PaginacionRequest request, InquilinoFilter filtro);
    Optional<InquilinoModel> obtenerPorDocumento(TipoDocumento tipoDocumento, String numeroDocumento);
    List<InquilinoModel> obtenerPorApartamento(Long idApartamento);
    int contarPorApartamento(Long idApartamento);
    void eliminarPorId(Long id);
}

