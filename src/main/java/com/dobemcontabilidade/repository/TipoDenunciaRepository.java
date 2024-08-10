package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TipoDenuncia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoDenuncia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDenunciaRepository extends JpaRepository<TipoDenuncia, Long>, JpaSpecificationExecutor<TipoDenuncia> {}
