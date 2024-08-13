package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AnexoRequerido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AnexoRequerido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnexoRequeridoRepository extends JpaRepository<AnexoRequerido, Long>, JpaSpecificationExecutor<AnexoRequerido> {}
