package com.condominios.sgc.application.usecase;

import java.util.List;

import com.condominios.sgc.domain.model.CondominioModel;

public interface ListarCondominiosUseCase {
    List<CondominioModel> ejecutar();
}
