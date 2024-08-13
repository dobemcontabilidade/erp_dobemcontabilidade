package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GrupoAcessoUsuarioContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GrupoAcessoUsuarioContador entity.
 */
@Repository
public interface GrupoAcessoUsuarioContadorRepository extends JpaRepository<GrupoAcessoUsuarioContador, Long> {
    default Optional<GrupoAcessoUsuarioContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GrupoAcessoUsuarioContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GrupoAcessoUsuarioContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select grupoAcessoUsuarioContador from GrupoAcessoUsuarioContador grupoAcessoUsuarioContador left join fetch grupoAcessoUsuarioContador.usuarioContador left join fetch grupoAcessoUsuarioContador.grupoAcessoPadrao",
        countQuery = "select count(grupoAcessoUsuarioContador) from GrupoAcessoUsuarioContador grupoAcessoUsuarioContador"
    )
    Page<GrupoAcessoUsuarioContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select grupoAcessoUsuarioContador from GrupoAcessoUsuarioContador grupoAcessoUsuarioContador left join fetch grupoAcessoUsuarioContador.usuarioContador left join fetch grupoAcessoUsuarioContador.grupoAcessoPadrao"
    )
    List<GrupoAcessoUsuarioContador> findAllWithToOneRelationships();

    @Query(
        "select grupoAcessoUsuarioContador from GrupoAcessoUsuarioContador grupoAcessoUsuarioContador left join fetch grupoAcessoUsuarioContador.usuarioContador left join fetch grupoAcessoUsuarioContador.grupoAcessoPadrao where grupoAcessoUsuarioContador.id =:id"
    )
    Optional<GrupoAcessoUsuarioContador> findOneWithToOneRelationships(@Param("id") Long id);
}
