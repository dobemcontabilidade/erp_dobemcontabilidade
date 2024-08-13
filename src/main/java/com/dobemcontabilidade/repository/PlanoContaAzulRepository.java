package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PlanoContaAzul;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoContaAzul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoContaAzulRepository extends JpaRepository<PlanoContaAzul, Long>, JpaSpecificationExecutor<PlanoContaAzul> {}
