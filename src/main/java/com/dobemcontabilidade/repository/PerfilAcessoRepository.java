package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PerfilAcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerfilAcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilAcessoRepository extends JpaRepository<PerfilAcesso, Long>, JpaSpecificationExecutor<PerfilAcesso> {}
