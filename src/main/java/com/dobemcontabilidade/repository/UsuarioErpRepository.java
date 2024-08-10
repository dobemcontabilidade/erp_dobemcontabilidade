package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.UsuarioErp;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UsuarioErp entity.
 */
@Repository
public interface UsuarioErpRepository extends JpaRepository<UsuarioErp, Long> {
    default Optional<UsuarioErp> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UsuarioErp> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UsuarioErp> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select usuarioErp from UsuarioErp usuarioErp left join fetch usuarioErp.administrador",
        countQuery = "select count(usuarioErp) from UsuarioErp usuarioErp"
    )
    Page<UsuarioErp> findAllWithToOneRelationships(Pageable pageable);

    @Query("select usuarioErp from UsuarioErp usuarioErp left join fetch usuarioErp.administrador")
    List<UsuarioErp> findAllWithToOneRelationships();

    @Query("select usuarioErp from UsuarioErp usuarioErp left join fetch usuarioErp.administrador where usuarioErp.id =:id")
    Optional<UsuarioErp> findOneWithToOneRelationships(@Param("id") Long id);
}
