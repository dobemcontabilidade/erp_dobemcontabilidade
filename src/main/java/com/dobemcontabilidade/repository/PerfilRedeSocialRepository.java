package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PerfilRedeSocial;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerfilRedeSocial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilRedeSocialRepository extends JpaRepository<PerfilRedeSocial, Long>, JpaSpecificationExecutor<PerfilRedeSocial> {}
