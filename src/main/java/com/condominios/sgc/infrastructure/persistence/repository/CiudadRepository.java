package com.condominios.sgc.infrastructure.persistence.repository;

import com.condominios.sgc.infrastructure.persistence.entity.CiudadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CiudadRepository extends JpaRepository<CiudadEntity, Long> {
    List<CiudadEntity> findByIdPais(Long idPais);
}
