package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Frequencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Frequencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, Long>, JpaSpecificationExecutor<Frequencia> {}
