package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresa;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermoContratoAssinaturaEmpresa entity.
 */
@Repository
public interface TermoContratoAssinaturaEmpresaRepository extends JpaRepository<TermoContratoAssinaturaEmpresa, Long> {
    default Optional<TermoContratoAssinaturaEmpresa> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TermoContratoAssinaturaEmpresa> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TermoContratoAssinaturaEmpresa> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select termoContratoAssinaturaEmpresa from TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa left join fetch termoContratoAssinaturaEmpresa.termoContratoContabil left join fetch termoContratoAssinaturaEmpresa.empresa",
        countQuery = "select count(termoContratoAssinaturaEmpresa) from TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa"
    )
    Page<TermoContratoAssinaturaEmpresa> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select termoContratoAssinaturaEmpresa from TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa left join fetch termoContratoAssinaturaEmpresa.termoContratoContabil left join fetch termoContratoAssinaturaEmpresa.empresa"
    )
    List<TermoContratoAssinaturaEmpresa> findAllWithToOneRelationships();

    @Query(
        "select termoContratoAssinaturaEmpresa from TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa left join fetch termoContratoAssinaturaEmpresa.termoContratoContabil left join fetch termoContratoAssinaturaEmpresa.empresa where termoContratoAssinaturaEmpresa.id =:id"
    )
    Optional<TermoContratoAssinaturaEmpresa> findOneWithToOneRelationships(@Param("id") Long id);
}
