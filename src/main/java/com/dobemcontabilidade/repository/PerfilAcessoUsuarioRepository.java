package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PerfilAcessoUsuario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerfilAcessoUsuario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerfilAcessoUsuarioRepository
    extends JpaRepository<PerfilAcessoUsuario, Long>, JpaSpecificationExecutor<PerfilAcessoUsuario> {}
