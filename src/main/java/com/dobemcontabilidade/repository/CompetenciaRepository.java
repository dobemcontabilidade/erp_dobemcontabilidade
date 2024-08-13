package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Competencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Competencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompetenciaRepository extends JpaRepository<Competencia, Long>, JpaSpecificationExecutor<Competencia> {}
