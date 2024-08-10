package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AreaContabil;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AreaContabil entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaContabilRepository extends JpaRepository<AreaContabil, Long> {}
