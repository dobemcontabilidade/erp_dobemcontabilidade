package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Ramo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Ramo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RamoRepository extends JpaRepository<Ramo, Long> {}
