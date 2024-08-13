package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.Denuncia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Denuncia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long>, JpaSpecificationExecutor<Denuncia> {}
