package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TermoAdesaoEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermoAdesaoEmpresa entity.
 */
@Repository
public interface TermoAdesaoEmpresaRepository extends JpaRepository<TermoAdesaoEmpresa, Long> {
    default Optional<TermoAdesaoEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TermoAdesaoEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TermoAdesaoEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select termoAdesaoEmpresa from TermoAdesaoEmpresa termoAdesaoEmpresa left join fetch termoAdesaoEmpresa.empresa left join fetch termoAdesaoEmpresa.planoContabil",
        countQuery = "select count(termoAdesaoEmpresa) from TermoAdesaoEmpresa termoAdesaoEmpresa"
    )
    Page<TermoAdesaoEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select termoAdesaoEmpresa from TermoAdesaoEmpresa termoAdesaoEmpresa left join fetch termoAdesaoEmpresa.empresa left join fetch termoAdesaoEmpresa.planoContabil"
    )
    List<TermoAdesaoEmpresa> findAllWithToOneRelationships();

    @Query(
        "select termoAdesaoEmpresa from TermoAdesaoEmpresa termoAdesaoEmpresa left join fetch termoAdesaoEmpresa.empresa left join fetch termoAdesaoEmpresa.planoContabil where termoAdesaoEmpresa.id =:id"
    )
    Optional<TermoAdesaoEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
