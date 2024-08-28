package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Contador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Contador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContadorRepository extends JpaRepository<Contador, Long> {}
