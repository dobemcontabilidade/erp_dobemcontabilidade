package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OpcaoNomeFantasiaEmpresa entity.
 */
@Repository
public interface OpcaoNomeFantasiaEmpresaRepository
    extends JpaRepository<OpcaoNomeFantasiaEmpresa, Long>, JpaSpecificationExecutor<OpcaoNomeFantasiaEmpresa> {
    default Optional<OpcaoNomeFantasiaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<OpcaoNomeFantasiaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<OpcaoNomeFantasiaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select opcaoNomeFantasiaEmpresa from OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa left join fetch opcaoNomeFantasiaEmpresa.empresa",
        countQuery = "select count(opcaoNomeFantasiaEmpresa) from OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa"
    )
    Page<OpcaoNomeFantasiaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select opcaoNomeFantasiaEmpresa from OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa left join fetch opcaoNomeFantasiaEmpresa.empresa"
    )
    List<OpcaoNomeFantasiaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select opcaoNomeFantasiaEmpresa from OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa left join fetch opcaoNomeFantasiaEmpresa.empresa where opcaoNomeFantasiaEmpresa.id =:id"
    )
    Optional<OpcaoNomeFantasiaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
