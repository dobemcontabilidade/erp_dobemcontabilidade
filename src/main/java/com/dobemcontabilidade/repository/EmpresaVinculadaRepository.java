package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.EmpresaVinculada;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmpresaVinculada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaVinculadaRepository extends JpaRepository<EmpresaVinculada, Long> {}
