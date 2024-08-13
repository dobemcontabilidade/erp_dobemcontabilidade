package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GrupoAcessoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoAcessoEmpresa entity.
 */
@Repository
public interface GrupoAcessoEmpresaRepository extends JpaRepository<GrupoAcessoEmpresa, Long> {
    default Optional<GrupoAcessoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GrupoAcessoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GrupoAcessoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select grupoAcessoEmpresa from GrupoAcessoEmpresa grupoAcessoEmpresa left join fetch grupoAcessoEmpresa.assinaturaEmpresa",
        countQuery = "select count(grupoAcessoEmpresa) from GrupoAcessoEmpresa grupoAcessoEmpresa"
    )
    Page<GrupoAcessoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select grupoAcessoEmpresa from GrupoAcessoEmpresa grupoAcessoEmpresa left join fetch grupoAcessoEmpresa.assinaturaEmpresa")
    List<GrupoAcessoEmpresa> findAllWithToOneRelationships();

    @Query(
        "select grupoAcessoEmpresa from GrupoAcessoEmpresa grupoAcessoEmpresa left join fetch grupoAcessoEmpresa.assinaturaEmpresa where grupoAcessoEmpresa.id =:id"
    )
    Optional<GrupoAcessoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
