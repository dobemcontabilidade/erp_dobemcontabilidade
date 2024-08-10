package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PlanoContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoContabilRepository extends JpaRepository<PlanoContabil, Long>, JpaSpecificationExecutor<PlanoContabil> {}
