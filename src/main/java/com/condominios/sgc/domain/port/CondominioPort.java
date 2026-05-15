package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PageRequestDto;
import com.condominios.sgc.domain.dto.PageResponseDto;
import com.condominios.sgc.domain.model.CondominioModel;

public interface CondominioPort {
    Optional<CondominioModel> findById(Long id);
    PageResponseDto<CondominioModel> findAll(PageRequestDto pageRequestDto);
    CondominioModel save(CondominioModel model);
    void deleteById(Long id);
    boolean existsByNombre(String nombre);
}
