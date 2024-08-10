package com.dobemcontabilidade.repository;

import com.dobemcontabilidade.domain.CalculoPlanoAssinatura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CalculoPlanoAssinatura entity.
 */
@Repository
public interface CalculoPlanoAssinaturaRepository
    extends JpaRepository<CalculoPlanoAssinatura, Long>, JpaSpecificationExecutor<CalculoPlanoAssinatura> {
    default Optional<CalculoPlanoAssinatura> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CalculoPlanoAssinatura> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CalculoPlanoAssinatura> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select calculoPlanoAssinatura from CalculoPlanoAssinatura calculoPlanoAssinatura left join fetch calculoPlanoAssinatura.periodoPagamento left join fetch calculoPlanoAssinatura.planoContaAzul left join fetch calculoPlanoAssinatura.planoContabil left join fetch calculoPlanoAssinatura.ramo left join fetch calculoPlanoAssinatura.tributacao left join fetch calculoPlanoAssinatura.descontoPlanoContabil left join fetch calculoPlanoAssinatura.descontoPlanoContaAzul left join fetch calculoPlanoAssinatura.assinaturaEmpresa",
        countQuery = "select count(calculoPlanoAssinatura) from CalculoPlanoAssinatura calculoPlanoAssinatura"
    )
    Page<CalculoPlanoAssinatura> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select calculoPlanoAssinatura from CalculoPlanoAssinatura calculoPlanoAssinatura left join fetch calculoPlanoAssinatura.periodoPagamento left join fetch calculoPlanoAssinatura.planoContaAzul left join fetch calculoPlanoAssinatura.planoContabil left join fetch calculoPlanoAssinatura.ramo left join fetch calculoPlanoAssinatura.tributacao left join fetch calculoPlanoAssinatura.descontoPlanoContabil left join fetch calculoPlanoAssinatura.descontoPlanoContaAzul left join fetch calculoPlanoAssinatura.assinaturaEmpresa"
    )
    List<CalculoPlanoAssinatura> findAllWithToOneRelationships();

    @Query(
        "select calculoPlanoAssinatura from CalculoPlanoAssinatura calculoPlanoAssinatura left join fetch calculoPlanoAssinatura.periodoPagamento left join fetch calculoPlanoAssinatura.planoContaAzul left join fetch calculoPlanoAssinatura.planoContabil left join fetch calculoPlanoAssinatura.ramo left join fetch calculoPlanoAssinatura.tributacao left join fetch calculoPlanoAssinatura.descontoPlanoContabil left join fetch calculoPlanoAssinatura.descontoPlanoContaAzul left join fetch calculoPlanoAssinatura.assinaturaEmpresa where calculoPlanoAssinatura.id =:id"
    )
    Optional<CalculoPlanoAssinatura> findOneWithToOneRelationships(@Param("id") Long id);
}
