package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.PisoModel;

import java.util.List;

public interface PisoRepositoryPort {
    List<PisoModel> listarPorTorre(Long idTorre);
}
