package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssinaturaEmpresa entity.
 */
@Repository
public interface AssinaturaEmpresaRepository extends JpaRepository<AssinaturaEmpresa, Long> {
    default Optional<AssinaturaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<AssinaturaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<AssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select assinaturaEmpresa from AssinaturaEmpresa assinaturaEmpresa left join fetch assinaturaEmpresa.periodoPagamento left join fetch assinaturaEmpresa.formaDePagamento left join fetch assinaturaEmpresa.empresa",
        countQuery = "select count(assinaturaEmpresa) from AssinaturaEmpresa assinaturaEmpresa"
    )
    Page<AssinaturaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select assinaturaEmpresa from AssinaturaEmpresa assinaturaEmpresa left join fetch assinaturaEmpresa.periodoPagamento left join fetch assinaturaEmpresa.formaDePagamento left join fetch assinaturaEmpresa.empresa"
    )
    List<AssinaturaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select assinaturaEmpresa from AssinaturaEmpresa assinaturaEmpresa left join fetch assinaturaEmpresa.periodoPagamento left join fetch assinaturaEmpresa.formaDePagamento left join fetch assinaturaEmpresa.empresa where assinaturaEmpresa.id =:id"
    )
    Optional<AssinaturaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
