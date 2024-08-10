package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.SecaoCnae;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SecaoCnae entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecaoCnaeRepository extends JpaRepository<SecaoCnae, Long>, JpaSpecificationExecutor<SecaoCnae> {}
