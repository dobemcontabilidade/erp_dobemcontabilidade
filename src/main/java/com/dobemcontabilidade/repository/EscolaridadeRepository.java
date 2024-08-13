package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Escolaridade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Escolaridade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscolaridadeRepository extends JpaRepository<Escolaridade, Long> {}
