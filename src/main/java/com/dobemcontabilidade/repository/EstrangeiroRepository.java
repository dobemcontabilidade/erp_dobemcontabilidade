package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Estrangeiro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Estrangeiro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstrangeiroRepository extends JpaRepository<Estrangeiro, Long> {}
