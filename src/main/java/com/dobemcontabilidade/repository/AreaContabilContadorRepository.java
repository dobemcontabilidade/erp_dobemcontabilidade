package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AreaContabilContador;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AreaContabilContador entity.
 */
@Repository
public interface AreaContabilContadorRepository extends JpaRepository<AreaContabilContador, Long> {
    default Optional<AreaContabilContador> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AreaContabilContador> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AreaContabilContador> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select areaContabilContador from AreaContabilContador areaContabilContador left join fetch areaContabilContador.contador left join fetch areaContabilContador.areaContabil",
        countQuery = "select count(areaContabilContador) from AreaContabilContador areaContabilContador"
    )
    Page<AreaContabilContador> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select areaContabilContador from AreaContabilContador areaContabilContador left join fetch areaContabilContador.contador left join fetch areaContabilContador.areaContabil"
    )
    List<AreaContabilContador> findAllWithToOneRelationships();

    @Query(
        "select areaContabilContador from AreaContabilContador areaContabilContador left join fetch areaContabilContador.contador left join fetch areaContabilContador.areaContabil where areaContabilContador.id =:id"
    )
    Optional<AreaContabilContador> findOneWithToOneRelationships(@Param("id") Long id);
}
