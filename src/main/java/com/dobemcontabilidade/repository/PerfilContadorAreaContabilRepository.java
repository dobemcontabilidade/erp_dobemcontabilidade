package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.PerfilContadorAreaContabil;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PerfilContadorAreaContabil entity.
 */
@Repository
public interface PerfilContadorAreaContabilRepository extends JpaRepository<PerfilContadorAreaContabil, Long> {
    default Optional<PerfilContadorAreaContabil> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PerfilContadorAreaContabil> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PerfilContadorAreaContabil> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select perfilContadorAreaContabil from PerfilContadorAreaContabil perfilContadorAreaContabil left join fetch perfilContadorAreaContabil.areaContabil left join fetch perfilContadorAreaContabil.perfilContador",
        countQuery = "select count(perfilContadorAreaContabil) from PerfilContadorAreaContabil perfilContadorAreaContabil"
    )
    Page<PerfilContadorAreaContabil> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select perfilContadorAreaContabil from PerfilContadorAreaContabil perfilContadorAreaContabil left join fetch perfilContadorAreaContabil.areaContabil left join fetch perfilContadorAreaContabil.perfilContador"
    )
    List<PerfilContadorAreaContabil> findAllWithToOneRelationships();

    @Query(
        "select perfilContadorAreaContabil from PerfilContadorAreaContabil perfilContadorAreaContabil left join fetch perfilContadorAreaContabil.areaContabil left join fetch perfilContadorAreaContabil.perfilContador where perfilContadorAreaContabil.id =:id"
    )
    Optional<PerfilContadorAreaContabil> findOneWithToOneRelationships(@Param("id") Long id);
}
