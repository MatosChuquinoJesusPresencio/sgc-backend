package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.domain.model.ApartamentoModel;

public interface ApartamentoRepositoryPort {
    Optional<ApartamentoModel> buscarPorId(Long id);
    ApartamentoModel guardar(ApartamentoModel modelo);
    void eliminarPorId(Long id);
    Optional<ApartamentoModel> buscarPorPropietario(Long idPropietario);
    Optional<ApartamentoModel> buscarPorNumeroYCondominio(Integer numero, Long idCondominio);
    PaginaResult<AdminApartamentoDetailResult> buscarEnCondominio(Long condominioId, PaginaQuery pagina);
}
