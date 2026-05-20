package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.PisoModel;

public interface PisoPort {

    PisoModel findById(Long id);
    PaginacionResponse<PisoModel> findByTorreId(Long torreId, PaginacionRequest pageRequest);
    PisoModel save(PisoModel piso);
    void deleteById(Long id);

}
