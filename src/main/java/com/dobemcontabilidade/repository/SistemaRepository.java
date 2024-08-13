package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Sistema;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Sistema entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SistemaRepository extends JpaRepository<Sistema, Long>, JpaSpecificationExecutor<Sistema> {}
