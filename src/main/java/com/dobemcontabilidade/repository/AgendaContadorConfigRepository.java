package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AgendaContadorConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AgendaContadorConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgendaContadorConfigRepository extends JpaRepository<AgendaContadorConfig, Long> {}
