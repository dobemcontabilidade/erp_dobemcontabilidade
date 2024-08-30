package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PlanoAssinaturaContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoAssinaturaContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoAssinaturaContabilRepository
    extends JpaRepository<PlanoAssinaturaContabil, Long>, JpaSpecificationExecutor<PlanoAssinaturaContabil> {}
