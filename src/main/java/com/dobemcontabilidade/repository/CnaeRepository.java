package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Cnae;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cnae entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CnaeRepository extends JpaRepository<Cnae, Long> {}
