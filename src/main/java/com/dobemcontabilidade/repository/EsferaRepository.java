package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Esfera;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Esfera entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsferaRepository extends JpaRepository<Esfera, Long>, JpaSpecificationExecutor<Esfera> {}
