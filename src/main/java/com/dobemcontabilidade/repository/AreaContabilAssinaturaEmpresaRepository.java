package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AreaContabilAssinaturaEmpresa entity.
 */
@Repository
public interface AreaContabilAssinaturaEmpresaRepository extends JpaRepository<AreaContabilAssinaturaEmpresa, Long> {
    default Optional<AreaContabilAssinaturaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AreaContabilAssinaturaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AreaContabilAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select areaContabilAssinaturaEmpresa from AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa left join fetch areaContabilAssinaturaEmpresa.areaContabil left join fetch areaContabilAssinaturaEmpresa.assinaturaEmpresa left join fetch areaContabilAssinaturaEmpresa.contador",
        countQuery = "select count(areaContabilAssinaturaEmpresa) from AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa"
    )
    Page<AreaContabilAssinaturaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select areaContabilAssinaturaEmpresa from AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa left join fetch areaContabilAssinaturaEmpresa.areaContabil left join fetch areaContabilAssinaturaEmpresa.assinaturaEmpresa left join fetch areaContabilAssinaturaEmpresa.contador"
    )
    List<AreaContabilAssinaturaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select areaContabilAssinaturaEmpresa from AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa left join fetch areaContabilAssinaturaEmpresa.areaContabil left join fetch areaContabilAssinaturaEmpresa.assinaturaEmpresa left join fetch areaContabilAssinaturaEmpresa.contador where areaContabilAssinaturaEmpresa.id =:id"
    )
    Optional<AreaContabilAssinaturaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
