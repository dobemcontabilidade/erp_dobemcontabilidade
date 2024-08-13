package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.GatewayAssinaturaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the GatewayAssinaturaEmpresa entity.
 */
@Repository
public interface GatewayAssinaturaEmpresaRepository extends JpaRepository<GatewayAssinaturaEmpresa, Long> {
    default Optional<GatewayAssinaturaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<GatewayAssinaturaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<GatewayAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select gatewayAssinaturaEmpresa from GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa left join fetch gatewayAssinaturaEmpresa.assinaturaEmpresa left join fetch gatewayAssinaturaEmpresa.gatewayPagamento",
        countQuery = "select count(gatewayAssinaturaEmpresa) from GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa"
    )
    Page<GatewayAssinaturaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select gatewayAssinaturaEmpresa from GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa left join fetch gatewayAssinaturaEmpresa.assinaturaEmpresa left join fetch gatewayAssinaturaEmpresa.gatewayPagamento"
    )
    List<GatewayAssinaturaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select gatewayAssinaturaEmpresa from GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa left join fetch gatewayAssinaturaEmpresa.assinaturaEmpresa left join fetch gatewayAssinaturaEmpresa.gatewayPagamento where gatewayAssinaturaEmpresa.id =:id"
    )
    Optional<GatewayAssinaturaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
