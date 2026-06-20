package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.ApartamentoModel;

import java.util.List;

public interface ApartamentoRepositoryPort {
    List<ApartamentoModel> listarPorPiso(Long idPiso);
}
