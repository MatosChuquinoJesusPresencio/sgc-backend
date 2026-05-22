package com.condominios.sgc.application.usecase;

import java.util.List;
import com.condominios.sgc.application.dto.InquilinoResponse;

public interface ListarInquilinosPorApartamentoUseCase {
    List<InquilinoResponse> listarPorApartamentoId(Long apartamentoId);
}
