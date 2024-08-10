package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.UsuarioContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the UsuarioContador entity.
 */
@Repository
public interface UsuarioContadorRepository extends JpaRepository<UsuarioContador, Long>, JpaSpecificationExecutor<UsuarioContador> {
    default Optional<UsuarioContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<UsuarioContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<UsuarioContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select usuarioContador from UsuarioContador usuarioContador left join fetch usuarioContador.contador left join fetch usuarioContador.administrador",
        countQuery = "select count(usuarioContador) from UsuarioContador usuarioContador"
    )
    Page<UsuarioContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select usuarioContador from UsuarioContador usuarioContador left join fetch usuarioContador.contador left join fetch usuarioContador.administrador"
    )
    List<UsuarioContador> findAllWithToOneRelationships();

    @Query(
        "select usuarioContador from UsuarioContador usuarioContador left join fetch usuarioContador.contador left join fetch usuarioContador.administrador where usuarioContador.id =:id"
    )
    Optional<UsuarioContador> findOneWithToOneRelationships(@Param("id") Long id);
}
