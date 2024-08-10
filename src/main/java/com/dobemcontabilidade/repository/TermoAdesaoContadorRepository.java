package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TermoAdesaoContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermoAdesaoContador entity.
 */
@Repository
public interface TermoAdesaoContadorRepository
    extends JpaRepository<TermoAdesaoContador, Long>, JpaSpecificationExecutor<TermoAdesaoContador> {
    default Optional<TermoAdesaoContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TermoAdesaoContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TermoAdesaoContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select termoAdesaoContador from TermoAdesaoContador termoAdesaoContador left join fetch termoAdesaoContador.contador left join fetch termoAdesaoContador.termoDeAdesao",
        countQuery = "select count(termoAdesaoContador) from TermoAdesaoContador termoAdesaoContador"
    )
    Page<TermoAdesaoContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select termoAdesaoContador from TermoAdesaoContador termoAdesaoContador left join fetch termoAdesaoContador.contador left join fetch termoAdesaoContador.termoDeAdesao"
    )
    List<TermoAdesaoContador> findAllWithToOneRelationships();

    @Query(
        "select termoAdesaoContador from TermoAdesaoContador termoAdesaoContador left join fetch termoAdesaoContador.contador left join fetch termoAdesaoContador.termoDeAdesao where termoAdesaoContador.id =:id"
    )
    Optional<TermoAdesaoContador> findOneWithToOneRelationships(@Param("id") Long id);
}
