package com.condominios.sgc.application.usecase;

import com.condominios.sgc.domain.dto.PageRequestDto;
import com.condominios.sgc.domain.dto.PageResponseDto;
import com.condominios.sgc.domain.model.CondominioModel;

public interface ListarCondominiosUseCase {
    PageResponseDto<CondominioModel> ejecutar(PageRequestDto request);
}
