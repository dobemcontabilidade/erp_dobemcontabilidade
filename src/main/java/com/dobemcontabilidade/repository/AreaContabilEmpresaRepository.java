package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AreaContabilEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AreaContabilEmpresa entity.
 */
@Repository
public interface AreaContabilEmpresaRepository extends JpaRepository<AreaContabilEmpresa, Long> {
    default Optional<AreaContabilEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AreaContabilEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AreaContabilEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select areaContabilEmpresa from AreaContabilEmpresa areaContabilEmpresa left join fetch areaContabilEmpresa.contador",
        countQuery = "select count(areaContabilEmpresa) from AreaContabilEmpresa areaContabilEmpresa"
    )
    Page<AreaContabilEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query("select areaContabilEmpresa from AreaContabilEmpresa areaContabilEmpresa left join fetch areaContabilEmpresa.contador")
    List<AreaContabilEmpresa> findAllWithToOneRelationships();

    @Query(
        "select areaContabilEmpresa from AreaContabilEmpresa areaContabilEmpresa left join fetch areaContabilEmpresa.contador where areaContabilEmpresa.id =:id"
    )
    Optional<AreaContabilEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
