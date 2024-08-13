package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CobrancaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CobrancaEmpresa entity.
 */
@Repository
public interface CobrancaEmpresaRepository extends JpaRepository<CobrancaEmpresa, Long> {
    default Optional<CobrancaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CobrancaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CobrancaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select cobrancaEmpresa from CobrancaEmpresa cobrancaEmpresa left join fetch cobrancaEmpresa.assinaturaEmpresa left join fetch cobrancaEmpresa.formaDePagamento",
        countQuery = "select count(cobrancaEmpresa) from CobrancaEmpresa cobrancaEmpresa"
    )
    Page<CobrancaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select cobrancaEmpresa from CobrancaEmpresa cobrancaEmpresa left join fetch cobrancaEmpresa.assinaturaEmpresa left join fetch cobrancaEmpresa.formaDePagamento"
    )
    List<CobrancaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select cobrancaEmpresa from CobrancaEmpresa cobrancaEmpresa left join fetch cobrancaEmpresa.assinaturaEmpresa left join fetch cobrancaEmpresa.formaDePagamento where cobrancaEmpresa.id =:id"
    )
    Optional<CobrancaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
