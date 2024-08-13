package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AtorAvaliado;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AtorAvaliado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AtorAvaliadoRepository extends JpaRepository<AtorAvaliado, Long> {}
