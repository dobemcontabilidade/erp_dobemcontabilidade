package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Estado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>, JpaSpecificationExecutor<Estado> {}
