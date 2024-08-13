package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.UsuarioEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UsuarioEmpresa entity.
 */
@Repository
public interface UsuarioEmpresaRepository extends JpaRepository<UsuarioEmpresa, Long>, JpaSpecificationExecutor<UsuarioEmpresa> {
    default Optional<UsuarioEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UsuarioEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UsuarioEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select usuarioEmpresa from UsuarioEmpresa usuarioEmpresa left join fetch usuarioEmpresa.assinaturaEmpresa",
        countQuery = "select count(usuarioEmpresa) from UsuarioEmpresa usuarioEmpresa"
    )
    Page<UsuarioEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select usuarioEmpresa from UsuarioEmpresa usuarioEmpresa left join fetch usuarioEmpresa.assinaturaEmpresa")
    List<UsuarioEmpresa> findAllWithToOneRelationships();

    @Query(
        "select usuarioEmpresa from UsuarioEmpresa usuarioEmpresa left join fetch usuarioEmpresa.assinaturaEmpresa where usuarioEmpresa.id =:id"
    )
    Optional<UsuarioEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
