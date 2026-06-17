package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.infrastructure.persistence.entity.InquilinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;
import java.util.Optional;

public interface InquilinoRepository extends JpaRepository<InquilinoEntity, Long>, JpaSpecificationExecutor<InquilinoEntity> {
    Optional<InquilinoEntity> findByTipoDocumentoAndNumeroDocumento(TipoDocumento tipoDocumento, String numeroDocumento);
    List<InquilinoEntity> findByIdApartamento(Long idApartamento);
    List<InquilinoEntity> findByIdApartamentoIn(List<Long> idsApartamento);
}
