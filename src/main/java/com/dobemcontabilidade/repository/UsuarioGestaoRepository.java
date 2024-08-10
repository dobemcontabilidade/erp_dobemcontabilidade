package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.UsuarioGestao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UsuarioGestao entity.
 */
@Repository
public interface UsuarioGestaoRepository extends JpaRepository<UsuarioGestao, Long>, JpaSpecificationExecutor<UsuarioGestao> {
    default Optional<UsuarioGestao> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UsuarioGestao> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UsuarioGestao> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select usuarioGestao from UsuarioGestao usuarioGestao left join fetch usuarioGestao.administrador",
        countQuery = "select count(usuarioGestao) from UsuarioGestao usuarioGestao"
    )
    Page<UsuarioGestao> findAllWithToOneRelationships(Pageable pageable);

    @Query("select usuarioGestao from UsuarioGestao usuarioGestao left join fetch usuarioGestao.administrador")
    List<UsuarioGestao> findAllWithToOneRelationships();

    @Query("select usuarioGestao from UsuarioGestao usuarioGestao left join fetch usuarioGestao.administrador where usuarioGestao.id =:id")
    Optional<UsuarioGestao> findOneWithToOneRelationships(@Param("id") Long id);
}
