package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Pais;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pais entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaisRepository extends JpaRepository<Pais, Long>, JpaSpecificationExecutor<Pais> {}
