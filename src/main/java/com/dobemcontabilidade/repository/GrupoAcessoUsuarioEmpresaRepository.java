package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoAcessoUsuarioEmpresa entity.
 */
@Repository
public interface GrupoAcessoUsuarioEmpresaRepository extends JpaRepository<GrupoAcessoUsuarioEmpresa, Long> {
    default Optional<GrupoAcessoUsuarioEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GrupoAcessoUsuarioEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GrupoAcessoUsuarioEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select grupoAcessoUsuarioEmpresa from GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa left join fetch grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa left join fetch grupoAcessoUsuarioEmpresa.usuarioEmpresa",
        countQuery = "select count(grupoAcessoUsuarioEmpresa) from GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa"
    )
    Page<GrupoAcessoUsuarioEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select grupoAcessoUsuarioEmpresa from GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa left join fetch grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa left join fetch grupoAcessoUsuarioEmpresa.usuarioEmpresa"
    )
    List<GrupoAcessoUsuarioEmpresa> findAllWithToOneRelationships();

    @Query(
        "select grupoAcessoUsuarioEmpresa from GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa left join fetch grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa left join fetch grupoAcessoUsuarioEmpresa.usuarioEmpresa where grupoAcessoUsuarioEmpresa.id =:id"
    )
    Optional<GrupoAcessoUsuarioEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
