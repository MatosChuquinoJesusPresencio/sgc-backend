package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.TorreModel;

import java.util.List;

public interface TorreRepositoryPort {
    List<TorreModel> listarPorCondominio(Long idCondominio);
}
